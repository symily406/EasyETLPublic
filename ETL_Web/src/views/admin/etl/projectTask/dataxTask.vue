<template>
	<div class="taks-main-container">
		<splitpanes class="default-theme" horizontal @resize="onResize">
			<pane :size="topSize" :min-size="minSize" id="paneTop">
				<table border="0" width="100%" cellspacing="0" callpadding="0">
					<tr>
						<td valign="top">
							<div class="task-run" v-if="taskStep==50&&compare==false">
								<table border="0" cellspacing="0" callpadding="0">
									<tr>
										<td width="20">&nbsp;</td>
										<td><el-button type="text" icon="el-icon-video-play" :loading="runLoading"
												class="button-padding-none" :disabled="isRun==1?true:false"
												@click="jobRun">运行</el-button></td>
										<td width="20">&nbsp;&nbsp;</td>
										<td><el-button type="text" icon="el-icon-video-pause" :loading="stopLoading"
												class="button-padding-none" :disabled="isRun==1?false:true"
												@click="jobStop">停止</el-button></td>
										<td width="20">&nbsp;&nbsp;</td>
										<td v-if="taskStep==50"><el-button type="text" icon="fa fa-exchange"
												class="button-padding-none" @click="createDataxJson"> 查看脚本</el-button>
										</td>
										<td width="20">&nbsp;&nbsp;</td>
										<td width="50"><el-button type="text" icon="fa fa-save" :loading="saveLoading"
												class="button-padding-none" @click="taskSubmit(50)">
												保存</el-button></td>
										<td width="20">&nbsp;&nbsp;</td>
										<td><el-button type="text" icon="el-icon-upload2" :loading="submitLoading"
												class="button-padding-none" @click="submitJob">提交</el-button>
										</td>
										<td width="20">&nbsp;&nbsp;</td>
									</tr>
								</table>
							</div>
							<el-steps :active="taskStep" :align-center="true" v-if="taskStep!=50">
								<el-step title="数据来源"></el-step>
								<el-step title="选择目录"></el-step>
								<el-step title="字段映射"></el-step>
								<el-step title="通道配置"></el-step>
								<el-step title="预览保存"></el-step>
							</el-steps>
							<div class="scrollable-container" :style="{height:(topHeight-80)+'px'}">
								<step01 :compare="compare" :backup="backup" ref="step01"
									@readColumnsCallBack="readColumnsCallBack" :projectId="projectId"
									:isDisabled="isDisabled" :taskStep="taskStep" @nextSetp="nextSetp"
									@parameterCallBack="parameterCallBack" @sourceTable="sourceTable"
									v-show="taskStep==1||taskStep==5||taskStep==50" />
								<step02 :compare="compare" :backup="backup" ref="step02"
									@writeColumnsCallBack="writeColumnsCallBack" :projectId="projectId"
									:isDisabled="isDisabled" :taskStep="taskStep" @nextSetp="nextSetp"
									@parameterCallBack="parameterCallBack"
									v-show="taskStep==2||taskStep==5||taskStep==50" />
								<step03 :compare="compare" :backup="backup" ref="step03" :projectId="projectId"
									:isDisabled="isDisabled" :taskStep="taskStep" @parameterCallBack="parameterCallBack"
									@nextSetp="nextSetp" v-show="taskStep==3||taskStep==5||taskStep==50" />
								<step04 :compare="compare" :backup="backup" ref="step04" :projectId="projectId"
									:isDisabled="isDisabled" :taskStep="taskStep" @nextSetp="nextSetp"
									v-show="taskStep==4||taskStep==5||taskStep==50" @taskSubmit="taskSubmit" />
								<div style="height: 10px; line-height: 10px;"></div>
							</div>
						</td>
						<td width="40" valign="top" :style="{height:(topHeight-80)+'px'}">
							<taskSetting ref="taskSettingRef" :compare="compare" :backup="backup" :evnForm="evnForm"
								:schedueForm="schedueForm" :projectTaskInfo="projectTaskInfo" :taskStep="taskStep"
								:failurePolicy="failurePolicy" :taskParameters="taskParameters"
								:globParameters="globParameters" :evnContent="evnContent" :isFlow="isFlow"
								:isEnableCluster="isEnableCluster" @closeDrawer="closeDrawer" />
						</td>
					</tr>
				</table>
			</pane>
			<pane :size="bottomSize" :min-size="minSize" id="paneBottom">
				<log ref="log" @logCloseCallBack="logCloseCallBack" @jobRunCallBack="jobRunCallBack" :headerHeight="35"
					:bottomHeight="bottomHeight" />
			</pane>
		</splitpanes>
		<dataxJson ref="dataxJson" dialogTitle="任务JSON" dialogWidth="80%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" />
		<submitFrom ref="submitFromRef" dialogTitle="任务提交" dialogWidth="300px" dialogHeight="100px"
			:dialogTop="this.$store.state.global.dialogTop" @callBack="submitCallBack"></submitFrom>
	</div>
</template>
<script>
	import {
		Splitpanes,
		Pane
	} from 'splitpanes'
	import 'splitpanes/dist/splitpanes.css'
	import {
		taskForm,
		splitPane
	} from "@/mixins/mixins";
	import step01 from './datax/step01';
	import step02 from './datax/step02';
	import step03 from './datax/step03';
	import step04 from './datax/step04';
	import taskSetting from './taskSetting';
	import dataxJson from './module/dataxJson';
	import log from "./module/log";
	import submitFrom from "./module/submitFrom";
	import {
		getTaskData,
		removeTaskData
	} from "@/utils/localStore.js";
	export default {
		name: "dataxTask",
		components: {
			step01,
			step02,
			step03,
			step04,
			taskSetting,
			dataxJson,
			log,
			submitFrom,
			Splitpanes,
			Pane
		},
		props: {

		},
		mixins: [taskForm, splitPane],
		data() {
			return {
				rules: {},
				taskStep: 0,
				isDisabled: false,
				folderTreeData: [],
				contentConnerHeight: 600,
				dbSources: [],
				readColumns: [],
				writeColumns: [],
				reloadTime: undefined,
				form: {
					taskId: '',
					enable: 1
				},
				isFlow: false,
				projectTaskInfo: {},
				sourceTableName: undefined
			}
		},
		//组件实例刚被创建,el和data并未初始化
		beforeCreate() {
			document.querySelector("html").setAttribute("style", "background: #fff;");
			document.querySelector("body").setAttribute("style", "background: #fff;");
		},
		//组件实例创建完成,完成了data数据的初始化,el没有
		created() {

		},
		//完成了el和data 初始化
		beforeMount() {

		},
		//完成挂载
		mounted() {
			let that = this;
			this.init();
			this.$refs['log'].showLogContentSwitch();
			window.addEventListener('resize', function() {
				that.$refs['step03'].initPlumbInsSet();
			});
		},
		//更新前状态
		beforeUpdate() {

		},
		//更新完成状态
		updated() {

		},
		//销毁前状态
		beforeDestroy() {},
		//销毁完成状态
		destroyed() {

		},
		watch: {
			$route: 'handleRoute',
			taskId(newVal, oldVal) {

			},
			taskStep(newVal, oldVal) {

			}
		},
		computed: {

		},
		methods: {
			setEqualSize() {
				this.topSize = 50
				this.bottomSize = 50
			},
			setTopLarge() {
				this.topSize = 70
				this.bottomSize = 30
			},

			setBottomLarge() {
				this.topSize = 30
				this.bottomSize = 70
			},
			handleRoute(route) {
				if (this.$route.query.reloadTime) {
					this.init();
				}
			},
			init() {
				let that = this;
				this.compare = this.$route.query.compare == 'true' ? true : false;
				this.backup = this.$route.query.backup == 'true' ? true : false;
				this.taskId = this.$route.query.taskId;
				this.projectId = this.$route.query.projectId;
				this.projectWorkSpaceLayerRelId = this.$route.query.projectWorkSpaceLayerRelId;
				this.projectWorkSpaceId = this.$route.query.projectWorkSpaceId;
				this.workSpaceType = this.$route.query.workSpaceType;
				this.initTask();
				this.queryTaskParameters({
					"taskId": this.taskId,
					"backup": this.backup
				}, function(res) {

				}, function(err) {

				});
				this.queryTaskSchedue({
					"taskId": this.taskId,
					"backup": this.backup
				}, function(res) {

				}, function(err) {

				});
				this.queryTaskEvn({
					"taskId": this.taskId,
					"backup": this.backup
				}, function(res) {
					console.log(res.data);
				}, function(err) {

				});
				this.initBackup();
				if (!this.compare) {
					this.queryRunnerJob(this.taskId, function(jobId, taskId) {
						that.jobId = jobId
						that.openLog(taskId, 1);
						that.$refs['log'].jobId = jobId;
						that.$refs['log'].initTaskJob(undefined);
					});
				}
			},
			initTask() {
				let url = "/admin/etl/projectTask/getById";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/getById";
				}
				this.loading = true;
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": this.taskId
				}).then(
					res => {
						this.projectTaskInfo = res.data;
						this.taskStep = res.data.taskStep;
						this.taskFolderId = res.data.taskFolderId;
						this.isEnableCluster = res.data.isEnableCluster;
						if (this.isEnableCluster == 0) {
							this.$refs['taskSettingRef'].setTabs(3);
						}else{
							this.$refs['taskSettingRef'].setTabs(0);
						}
						this.nextSetp(this.taskStep);
						this.loading = false;
					}).catch(err => {
					this.loading = false;
				});
			},
			initStepPage() {
				if (this.taskStep == 1 || this.taskStep == 5 || this.taskStep == 50) {
					this.$refs['step01'].destroy();
					this.$refs['step01'].taskId = this.taskId;
					this.$refs['step01'].form.projectId = this.projectId;
					this.$refs['step01'].form.projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					this.$refs['step01'].form.projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs['step01'].form.workSpaceType = this.workSpaceType;
					this.$refs['step01'].form.taskFolderId = this.taskFolderId;
					this.$refs['step01'].initDbSource();
				}
				if (this.taskStep == 2 || this.taskStep == 5 || this.taskStep == 50) {
					this.$refs['step02'].destroy();
					this.$refs['step02'].taskId = this.taskId;
					this.$refs['step02'].form.projectId = this.projectId;
					this.$refs['step02'].form.projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					this.$refs['step02'].form.projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs['step02'].form.workSpaceType = this.workSpaceType;
					this.$refs['step02'].form.taskFolderId = this.taskFolderId;
					this.$refs['step02'].initDbSource();
					this.$refs['step02'].initTaskRead();
				}
				if (this.taskStep == 3 || this.taskStep == 5 || this.taskStep == 50) {
					this.$refs['step03'].destroy();
					this.$refs['step03'].initPlumbIns();
					this.$refs['step03'].initPlumbInsSet();
					this.$refs['step03'].taskId = this.taskId;
					this.$refs['step03'].projectId = this.projectId;
					this.$refs['step03'].projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					this.$refs['step03'].projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs['step03'].workSpaceType = this.workSpaceType;
					this.$refs['step03'].taskFolderId = this.taskFolderId;
					this.$refs['step03'].initReadField();
				}
				if (this.taskStep == 4 || this.taskStep == 5 || this.taskStep == 50) {
					this.$refs['step04'].destroy();
					this.$refs['step04'].taskId = this.taskId;
					this.$refs['step04'].form.projectId = this.projectId;
					this.$refs['step04'].form.projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					this.$refs['step04'].form.projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs['step04'].form.workSpaceType = this.workSpaceType;
					this.$refs['step04'].form.taskFolderId = this.taskFolderId;
					this.$refs['step04'].init();
				}
			},
			readColumnsCallBack(columns) {
				this.readColumns = columns;
			},
			writeColumnsCallBack(columns) {
				this.writeColumns = columns;
			},
			sourceTable(tableName) {
				this.sourceTableName = tableName;
			},
			nextSetp(taskStep) {
				this.taskStep = taskStep;
				this.isDisabled = false;
				if (taskStep == 5) {
					this.isDisabled = true;
				}
				this.initStepPage();
			},
			parameterCallBack() {
				let parameters = [];
				let step01 = this.getData("step01");
				let step02 = this.getData("step02");
				if (step01) {
					Object.values(JSON.parse(step01)).forEach(value => {
						parameters = parameters.concat(this.$app.parameterParse(value));
					});
				}
				if (step02) {
					Object.values(JSON.parse(step02)).forEach(value => {
						parameters = parameters.concat(this.$app.parameterParse(value));
					});
				}
				const uniqueArray = parameters.filter((item, index) => parameters.indexOf(item) === index);
				let oldTaskParameters = this.taskParameters;
				uniqueArray.forEach(key => {
					if (!oldTaskParameters.filter(item => item.paramKey == key)[0]) {
						oldTaskParameters.push({
							taskId: this.taskId,
							projectId: this.projectId,
							projectWorkSpaceLayerRelId: this.projectWorkSpaceLayerRelId,
							projectWorkSpaceId: this.projectWorkSpaceId,
							workSpaceType: this.workSpaceType,
							taskFolderId: this.taskFolderId,
							paramKey: key,
							paramValue: '',
							paramType: 0
						});
					}
				});
				let taskParameters = [];
				uniqueArray.forEach(key => {
					let taskParameter = oldTaskParameters.filter(item => item.paramKey == key)[0];
					if (taskParameter) {
						taskParameters.push(taskParameter);
					}
				});
				this.taskParameters = taskParameters;
			},
			getData(key) {
				return getTaskData(key + "_" + this.taskId);
			},
			removeStepData() {
				removeTaskData("step01_" + this.taskId);
				removeTaskData("step02_" + this.taskId);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			checkTask() {
				let tips = "";
				let checked = true;
				for (let i = 0; i < this.taskParameters.length; i++) {
					let item = this.taskParameters[i];
					if (!item.paramValue) {
						tips = "请配置任务参数:" + item.paramKey + "的值";
						checked = false;
						break;
					}
				}
				if (!checked) {
					this.$message.error(tips);
					return false;
				}
				return true;
			},
			taskSubmit(taskStep) {
				if (!this.checkTask()) {
					return;
				}
				this.saveLoading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/projectTask/saveTask", {
					taskId: this.taskId,
					cornStatus: this.schedueForm.cornStatus,
					startDate: this.schedueForm.startDate,
					endDate: this.schedueForm.endDate,
					corn: this.schedueForm.corn,
					taskParameters: JSON.stringify(this.taskParameters),
					taskStep: taskStep,
					schedue: JSON.stringify(this.schedueForm),
					evnContent: this.evnForm.evnContent
				}).then(
					res => {
						this.taskStep = taskStep;
						this.$message({
							message: '任务保存成功!',
							type: 'success'
						});
						this.removeStepData();
						this.saveLoading = false;
					}).catch(err => {
					this.saveLoading = false;
				});
			},
			submitJob() {
				if (!this.checkTask()) {
					return;
				}
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				this.$refs['submitFromRef'].dialogVisible = true;
			},
			submitCallBack(remarks) {
				this.submitLoading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/taskJob/submitJob", {
					"taskId": this.taskId,
					"remarks": remarks
				}).then(
					res => {
						this.submitLoading = false;
						this.$message({
							message: '任务提交成功',
							type: 'success'
						});
						this.initBackup();
					}).catch(err => {
					this.submitLoading = false;
				});
			},
			createDataxJson() {
				this.$refs['dataxJson'].loading = true;
				this.$refs['dataxJson'].taskId = this.taskId;
				this.$refs['dataxJson'].dialogVisible = true;
			},
			jobRun() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				this.runLoading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/runTaskJob/saveOrUpdate", {
					"taskId": taskId
				}).then(
					res => {
						let message = "任务运行成功！";
						if (res.data.isOldRunner) {
							message = "任务正在运行！";
						}
						this.$message({
							message: message,
							type: 'success'
						});
						this.jobId = res.data.jobId;
						this.$refs['log'].jobId = res.data.jobId;;
						this.openLog(this.taskId, 1);
					}).catch(err => {
					this.runLoading = false;
				});
			},
			jobStop() {
				this.$confirm('您确定要停止任务吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.stopLoading = true;
					this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJob/jobStop", {
						"jobId": this.jobId
					}).then(
						res => {
							this.stopLoading = false;
							this.isRun = false;
							this.runLoading = false;
						}).catch(err => {
						this.isRun = false;
					});
				}).catch(() => {
					this.stopLoading = false;
				});

			},
			jobRunCallBack() {
				this.isRun = false;
				this.runLoading = false;
				this.stopLoading = false;
			},
			logCloseCallBack() {
				this.closeBottom();
			},
			initBackup() {
				this.$refs['taskSettingRef'].initBackup();
			},
			closeDrawer() {

			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .el-dialog__header {
		padding: 0px;
		padding-top: 10px;
		padding-left: 10px;
	}

	::v-deep .el-dialog__body {
		padding: 5px;
	}
</style>