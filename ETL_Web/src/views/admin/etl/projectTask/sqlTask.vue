<template>
	<div class="taks-main-container" ref="sqlTaskContainer">
		<splitpanes class="default-theme" horizontal @resize="onResize">
			<pane :size="topSize" :min-size="minSize" id="paneTop">
				<table border="0" width="100%" cellspacing="0" callpadding="0">
					<tr>
						<td valign="top">
							<div class="task-run">
								<table border="0" cellspacing="0" callpadding="0" width="100%">
									<tr>
										<td width="20">&nbsp;</td>
										<td>
											<table border="0" cellspacing="0" callpadding="0" v-if="compare==false">
												<tr>
													<td width="50"><el-button type="text" icon="el-icon-video-play"
															:loading="runLoading" class="button-padding-none"
															:disabled="isRun==1?true:false"
															@click="sqlRun">运行</el-button></td>
													<td width="20">&nbsp;&nbsp;</td>
													<td width="50"><el-button type="text" icon="el-icon-video-pause"
															:loading="stopLoading" class="button-padding-none"
															:disabled="isRun==1?false:true"
															@click="sqlStop">停止</el-button></td>
													<td width="20">&nbsp;&nbsp;</td>
													<td width="50"><el-button type="text"
															:icon="formatLoading?'el-icon-loading':'el-icon-menu'"
															class="button-padding-none" @click="sqlFormat">
															格式化</el-button></td>
													<td width="20">&nbsp;&nbsp;</td>
													<td width="50"><el-button type="text" icon="fa fa-save"
															:loading="saveLoading" class="button-padding-none"
															@click="taskSubmit(50)">
															保存</el-button></td>
													<td width="20">&nbsp;&nbsp;</td>
													<td width="50"><el-button type="text" icon="el-icon-upload2"
															:loading="submitLoading" class="button-padding-none"
															@click="submitJob">提交</el-button>
													</td>
												</tr>
											</table>
										</td>
										<td>&nbsp;</td>
										<td></td>
										<td width="80" align="right">数据源：</td>
										<td width="200">
											<el-select v-model="form.dbSourceId" placeholder="请选择数据源" size="small"
												filterable :disabled="taskStep==50?true:false"
												class="max-width-100-ratio" @change="dbTableHandleChange">
												<el-option v-for="item in dbSources" :key="item.dbSourceId"
													:label="item.label" :value="item.dbSourceId">
												</el-option>
											</el-select>
										</td>
										<td width="30">&nbsp;&nbsp;</td>
									</tr>
								</table>

							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" cellspacing="0" callpadding="0">
								<tr>
									<td valign="top">
										<codeEditor ref="codeEditor" :style="{height:topHeight+'px'}"
											@change="codeChange" v-model="sqlCode" language="sql"></codeEditor>
									</td>
									<td width="40" valign="top" :style="{height:(topHeight-20)+'px'}">
										<taskSetting ref="taskSettingRef" :compare="compare" :backup="backup"
											:evnForm="evnForm" :schedueForm="schedueForm"
											:projectTaskInfo="projectTaskInfo" :taskStep="taskStep"
											:failurePolicy="failurePolicy" :taskParameters="taskParameters"
											:evnContent="evnContent" :globParameters="globParameters" :isFlow="isFlow"
											:isEnableCluster="isEnableCluster" @closeDrawer="closeDrawer" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</pane>
			<pane :size="bottomSize" :min-size="minSize" id="paneBottom">
				<log ref="log" @logCloseCallBack="logCloseCallBack" @jobRunCallBack="jobRunCallBack" :headerHeight="35"
					:bottomHeight="bottomHeight" />
			</pane>
		</splitpanes>

		<runCode ref="runCode" dialogTitle="任务" dialogWidth="80%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @runCodeCallBack="runCodeCallBack" />
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
	import codeEditor from "@/components/monaco/monaco.vue";
	import {
		format
	} from "sql-formatter";
	import log from "./module/log"
	import runCode from "./module/runCode.vue";
	import taskSetting from './taskSetting';
	import submitFrom from "./module/submitFrom";
	export default {
		name: "sqlTask",
		components: {
			codeEditor,
			runCode,
			log,
			taskSetting,
			submitFrom,
			Splitpanes,
			Pane
		},
		props: {

		},
		mixins: [taskForm, splitPane],
		data() {
			return {
				taskStep: 0,
				rules: {},
				showLogContainer: false,
				stopLoading: false,
				jobId: undefined,
				logTableHeight: 0,
				dbSources: [],
				isRun: 0,
				sqlCode: '',
				isDisabled: false,
				dbType: '',
				compare: false,
				backup: false,
				reloadTime: undefined,
				form: {
					taskId: '',
					dbSourceId: '',
					scriptContent: '',
					projectId: ''
				},
				projectTaskInfo: {}
			}
		},
		//组件实例刚被创建,el和data并未初始化
		beforeCreate() {

		},
		//组件实例创建完成,完成了data数据的初始化,el没有
		created() {

		},
		//完成了el和data 初始化
		beforeMount() {

		},
		//完成挂载
		mounted() {
			this.initContentHeight();
			this.init();
			window.addEventListener('resize', this.initContentHeight);
		},
		//更新前状态
		beforeUpdate() {

		},
		//更新完成状态
		updated() {

		},
		//销毁前状态
		beforeDestroy() {
			window.removeEventListener('resize', this.initContentHeight);
		},
		//销毁完成状态
		destroyed() {

		},
		watch: {
			$route: 'handleRoute',
			taskId(newVal, oldVal) {

			}
		},
		methods: {
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
					"backup": this.backup,
					"taskId": this.taskId
				}, function(res) {

				}, function(err) {

				});
				this.queryTaskEvn({
					"taskId": this.taskId,
					"backup": this.backup
				}, function(res) {

				}, function(err) {

				});
				this.initBackup();
				if (!this.compare) {
					this.queryRunnerJob(this.taskId, function(jobId, taskId) {
						that.jobId = jobId
						that.openLog(taskId, 2);
						that.$refs['log'].jobId = jobId;
						that.$refs['log'].initTaskJob(undefined);
					});
				}
			},
			initTask() {
				this.loading = true;
				let url = "/admin/etl/projectTask/getById";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/getById";
				}
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": this.taskId
				}).then(
					res => {
						if (res.data) {
							this.projectTaskInfo = res.data;
							this.taskStep = res.data.taskStep;
							this.taskFolderId = res.data.taskFolderId;
							this.form.dbSourceId = res.data.dbSourceId;
							this.dbSourceId = res.data.dbSourceId;
							this.isEnableCluster = res.data.isEnableCluster;
							if (this.isEnableCluster == 0) {
								this.$refs['taskSettingRef'].setTabs(3);
							} else {
								this.$refs['taskSettingRef'].setTabs(0);
							}
							this.isDisabled = true;
						}
						this.loading = false;
						this.initDbSource();
						this.initTaskScript();
					}).catch(err => {
					this.loading = false;
				});
				this.queryTaskSchedue({
					"taskId": this.taskId,
					"backup": this.backup
				}, function(res) {

				}, function(err) {

				});
			},
			initDbSource() {
				if (!this.taskId) {
					return;
				}
				let projectId = this.projectId;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/queryProjectDbSource", {
					"projectId": projectId
				}).then(
					res => {
						this.dbSources = res.data;
						this.dbTableHandleChange(this.form.dbSourceId);
					}).catch(err => {

				});
			},
			initTaskScript() {
				if (!this.taskId) {
					return;
				}
				let url = "/admin/etl/projectTaskScript/getById";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryProjectTaskScriptBackupById";
				}
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": this.taskId
				}).then(res => {
					this.form.scriptContent = res.data.scriptContent;
					this.sqlCode = res.data.scriptContent;
				}).catch(err => {

				});
			},
			dbTableHandleChange(val) {
				this.dbType = "mysql";
				let db = this.dbSources.filter(v => v.dbSourceId == val)[0];
				if (db) {
					this.dbType = db.sourceTypeName;
				}
				this.dbSourceId = val;
			},
			codeChange(code) {
				this.form.scriptContent = code;
				this.parseParameter(code)
			},
			parseParameter(content) {
				let parameters = this.$app.parameterParse(content);
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
			initContentHeight() {
				this.$nextTick(() => {
					this.contentConnerHeight = window.innerHeight;
				});
			},
			sqlFormat() {
				this.sqlCode = format(this.form.scriptContent);
			},
			sqlRun() {
				let runSql = this.$refs.codeEditor.getSelectCode();
				if (!runSql) {
					runSql = this.form.scriptContent;
				}
				// console.log(runSql);
				if (!runSql) {
					this.$message({
						message: '运行的脚本不能为空',
						type: 'warning'
					});
					return;
				}
				if (!this.dbSourceId) {
					this.$message({
						message: '请选择数据源',
						type: 'warning'
					});
					return;
				}
				this.$refs['runCode'].sqlCode = runSql;
				this.$refs['runCode'].taskId = this.taskId;
				this.$refs['runCode'].projectId = this.projectId;
				this.$refs['runCode'].projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
				this.$refs['runCode'].projectWorkSpaceId = this.projectWorkSpaceId;
				this.$refs['runCode'].workSpaceType = this.workSpaceType;
				this.$refs['runCode'].dbType = this.dbType;
				this.$refs['runCode'].dbSourceId = this.dbSourceId;
				this.$refs['runCode'].scriptContent = runSql;
				this.$refs['runCode'].dialogVisible = true;
			},
			sqlStop() {
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
			runCodeCallBack(jobId) {
				this.jobId = jobId;
				this.$refs['log'].jobId = jobId;
				this.openLog(this.taskId, 2);
			},
			logCloseCallBack() {
				this.closeBottom();
			},
			jobRunCallBack() {
				this.isRun = false;
				this.runLoading = false;
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
			taskSubmit(taskStep) {
				if (!this.dbSourceId) {
					this.$message({
						message: '请选择数据源',
						type: 'warning'
					});
					return;
				}
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
					scriptContent: this.form.scriptContent,
					dbSourceId: this.dbSourceId,
					schedue: JSON.stringify(this.schedueForm),
					evnContent: this.evnForm.evnContent
				}).then(
					res => {
						this.$message({
							message: '任务保存成功!',
							type: 'success'
						});
						this.taskStep = taskStep;
						this.saveLoading = false;
					}).catch(err => {
					this.saveLoading = false;
				});
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
			getContentPath() {
				return this.$app.global.etlPath;
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

</style>