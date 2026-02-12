<template>
	<div class="taks-main-container bg-white">
		<splitpanes class="default-theme" horizontal @resize="onResize">
			<pane :size="topSize" :min-size="minSize" id="paneTop">
				<table border="0" cellspacing="0" callpadding="0" width="100%">
					<tr>
						<td class="task-run">
							<table border="0" cellspacing="0" callpadding="0" v-if="compare==false">
								<tr>
									<td width="20">&nbsp;</td>
									<td width="50"><el-button type="text" icon="el-icon-video-play"
											:loading="runLoading" class="button-padding-none"
											:disabled="isRun==1?true:false" @click="flowRun">运行</el-button></td>
									<td width="20">&nbsp;&nbsp;</td>
									<td width="50"><el-button type="text" icon="el-icon-video-pause"
											:loading="stopLoading" class="button-padding-none"
											:disabled="isRun==1?false:true" @click="flowStop">停止</el-button></td>
									<td width="20">&nbsp;&nbsp;</td>
									<td width="50"><el-button type="text" icon="fa fa-save" :loading="saveLoading"
											class="button-padding-none" @click="saveFlow">
											保存</el-button></td>
									<td width="20">&nbsp;&nbsp;</td>
									<td><el-button type="text" icon="el-icon-upload2" :loading="submitLoading"
											class="button-padding-none" @click="submitJob">提交</el-button></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" cellspacing="0" callpadding="0" width="100%">
								<tr>
									<td>
										<flowPanel ref="flowPanel" @dbClickCallBack="dbClickCallBack"
											:height="topHeight" @nodeParamsCallBack="nodeParamsCallBack"
											:textEdit="false" :compare="compare">
										</flowPanel>
									</td>
									<td width="40" valign="top" :style="{height:(topHeight-20)+'px'}">
										<taskSetting ref="taskSettingRef" :compare="compare" :backup="backup"
											:evnForm="evnForm" :schedueForm="schedueForm"
											:projectTaskInfo="projectTaskInfo" :taskStep="taskStep"
											:failurePolicy="failurePolicy" :taskParameters="taskParameters"
											:globParameters="globParameters" :isFlow="isFlow"
											@closeDrawer="closeDrawer" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</pane>
			<pane :size="bottomSize" :min-size="minSize" id="paneBottom">
				<log ref="log" @logCloseCallBack="logCloseCallBack" @jobRunCallBack="jobRunCallBack"
					@flowRunLog="flowRunLog" :headerHeight="40" :isFlow="true" :bottomHeight="bottomHeight" />
			</pane>
		</splitpanes>

		<flowTaskList ref="flowTaskList" dialogTitle="任务选择" dialogWidth="1000px" dialogHeight="400"
			:dialogTop="this.$store.state.global.dialogTop" @taskCallBack="taskCallBack"></flowTaskList>

		<submitFrom ref="submitFromRef" dialogTitle="任务提交" dialogWidth="300px" dialogHeight="100px"
			:dialogTop="this.$store.state.global.dialogTop" @callBack="submitFlowTask"></submitFrom>
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
	import flowPanel from "@/components/logicflow/FlowPanel";
	import flowTaskList from "./module/flowTaskList";
	import taskSetting from './taskSetting';
	import log from "./module/log"
	import submitFrom from "./module/submitFrom";
	export default {
		name: "flowTask",
		components: {
			flowPanel,
			flowTaskList,
			taskSetting,
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
				taskStep: 0,
				rules: {},
				compare: false,
				backup: false,
				//流程
				lf: null,
				nodeId: null,
				isFlow: true,
				flowContent: [],
				codeContainerHeight: 0,
				logContainerHeight: 0,
				logTableHeight: 0,
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
			this.init();
			this.$refs['log'].showLogContentSwitch();
		},
		//更新前状态
		beforeUpdate() {

		},
		//更新完成状态
		updated() {

		},
		//销毁前状态
		beforeDestroy() {

		},
		//销毁完成状态
		destroyed() {

		},
		watch: {
			$route: 'handleRoute'
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
				this.initFlow();
				this.queryTaskSchedue({
					"taskId": this.taskId
				}, function(res) {

				}, function(err) {

				});
				this.initBackup();
				this.$nextTick(() => {
					this.$refs['flowPanel'].setMenu();
				});
				if (!this.compare) {
					this.queryRunnerFlow(this.taskId, function(flowRunJobId, taskId) {
						that.flowRunId = flowRunJobId;
						that.$refs['log'].flowRunId = flowRunJobId;
						that.$refs['log'].isFlow = true;
						that.openLog(taskId, 3);
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
						this.projectTaskInfo = res.data;
						this.$refs['taskSettingRef'].setTabs(3);
						this.loading = false;
					}).catch(err => {
					this.loading = false;
				});
			},
			initFlow() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				this.loading = true;
				let url = "/admin/etl/projectTaskFlow/getById";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryProjectTaskFlowBackupById";
				}
				this.$ajax.get(this.getContentPath() + url, {
					taskId: this.taskId
				}).then(
					res => {
						this.loading = false;
						if (res.data) {
							let flowContent = res.data.flowContent;
							this.flowContent = JSON.parse(flowContent);
							this.$refs['flowPanel'].render(this.flowContent);
							this.nodeParamsCallBack();
							this.clearFlowNodeIcon();
						}
					}).catch(err => {
					this.loading = false;
				});
			},
			initFlowNodeParameters(taskIds) {
				if (!taskIds) {
					return;
				}
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskFlow/queryNodeParameters", {
					taskIds: taskIds
				}).then(
					res => {
						if (res.data) {
							this.taskParameters = res.data;
						}
					}).catch(err => {

				});
			},
			dbClickCallBack(data) {
				let node = data.properties;
				if (node.nodeType == this.$store.state.global.flowStartNodeType || node.nodeType == this.$store.state
					.global.flowEndNodeType) {
					let tips = "开始节点不能编辑";
					switch (node.nodeType) {
						case this.$store.state.global.flowEndNodeType:
							tips = "结束节点不能编辑";
							break;
					}
					this.$message({
						message: tips,
						type: 'warning'
					});
					return;
				}
				this.nodeId = data.id;
				this.$refs['flowTaskList'].queryParams.workSpaceType = node.nodeType;
				this.$refs['flowTaskList'].dialogVisible = true;
			},
			taskCallBack(row) {
				this.$refs['flowPanel'].updateNodeProperties(this.nodeId, {
					"taskId": row.taskId,
					"projectId": row.projectId,
					"projectWorkSpaceLayerRelId": row.projectWorkSpaceLayerRelId,
					"projectWorkSpaceId": row.projectWorkSpaceId,
					"workSpaceType": row.workSpaceType,
					"taskFolderId": row.taskFolderId,
					"text": row.taskName,
				});
				this.$refs['flowTaskList'].dialogVisible = false;
				this.nodeParamsCallBack();
			},
			nodeParamsCallBack() {
				let flowData = this.$refs['flowPanel'].getFlowNodes();
				let nodes = flowData.nodes;
				let taskIds = [];
				for (let i = 0; i < nodes.length; i++) {
					let node = nodes[i];
					// console.log(JSON.stringify(node))
					let taskId = node.properties.taskId;
					if (taskId) {
						taskIds.push(taskId);
					}
				}
				this.initFlowNodeParameters(taskIds.join(","));
			},
			flowRun() {
				this.runLoading = true;
				this.clearFlowNodeIcon();
				this.$ajax.post(this.getContentPath() + "/admin/etl/projectTaskFlow/runFlowTask", {
					taskId: this.taskId
				}).then(
					res => {
						let isOldRunner = res.data.isOldRunner;
						let message = "任务运行成功!";
						if (isOldRunner) {
							message = "任务正在运行!";
						}
						this.$message({
							message: message,
							type: 'success'
						});
						this.flowRunId = res.data.flowRunId;
						this.$refs['log'].flowRunId = res.data.flowRunId;
						this.$refs['log'].isFlow = true;
						this.loading = false;
						this.openLog(this.taskId, 3);
					}).catch(err => {
					this.loading = false;
				});
			},
			flowStop() {
				this.$confirm('您确定要停止任务吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.stopLoading = true;
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskFlowRun/stopTaskFlow", {
						"flowRunId": this.flowRunId
					}).then(
						res => {
							this.stopLoading = false;
							this.isRun = false;
							this.runLoading = false;
							this.clearFlowNodeIcon();
						}).catch(err => {
						this.isRun = false;
					});
				}).catch(() => {
					this.stopLoading = false;
				});
			},
			clearFlowNodeIcon() {
				let flowPanel = this.$refs['flowPanel'];
				let nodes = this.flowContent.nodes;
				nodes.forEach(item => {
					console.log(item)
					if (item.nodeType != 99999 && item.nodeType != 0) {
						flowPanel.updateNodeProperties(item.id, {
							"icon": "",
							"iconColer": "#ffffff"
						});
					}
				});
			},
			saveFlow() {
				this.clearFlowNodeIcon();
				let flowPanel = this.$refs['flowPanel'];
				let flowData = flowPanel.getFlowNodes();
				if (!flowData) {
					return;
				}
				let edgeSourceNodes = [];
				let edgeTargetNodes = [];
				flowData.edges.forEach(item => {
					edgeSourceNodes.push(item.sourceNodeId);
					edgeTargetNodes.push(item.targetNodeId);
				});
				let tips = "";
				let checked = true;
				for (let i = 0; i < flowData.nodes.length; i++) {
					const item = flowData.nodes[i];
					switch (item.properties.nodeType) {
						case 0:
							if (edgeSourceNodes.findIndex(node => node == item.id) == -1) {
								tips = "请为开始节点配置目标节点";
								checked = false;
							}
							break;
						case 99999:
							if (edgeTargetNodes.findIndex(node => node == item.id) == -1) {
								tips = "请为结束节点配置来源节点";
								checked = false;
							}
							break;
						default:
							if (edgeTargetNodes.findIndex(node => node == item.id) == -1) {
								tips = "请为" + item.properties.text + "节点配置来源节点";
								checked = false;
								break;
							}
							if (edgeSourceNodes.findIndex(node => node == item.id) == -1) {
								tips = "请为" + item.properties.text + "节点配置目标节点";
								checked = false;
							}
					}

					if (!checked) {
						console.log(item.id);
						flowPanel.updateNodeProperties(item.id, {
							"icon": "el-icon-error",
							"iconColer": "#f56c6c"
						});
						break;
					}
				}
				if (!checked) {
					this.$message.error(tips);
					return;
				}
				let flowContent = JSON.stringify(flowData);
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
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
				if (!this.schedueForm.corn) {
					this.$message.error("请配置调度时间");
					return;
				}
				this.saveLoading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/projectTaskFlow/saveOrUpdate", {
					taskId: this.taskId,
					projectId: this.projectId,
					projectWorkSpaceLayerRelId: this.projectWorkSpaceLayerRelId,
					projectWorkSpaceId: this.projectWorkSpaceId,
					workSpaceType: this.workSpaceType,
					taskFolderId: this.taskFolderId,
					flowContent: flowContent,
					taskParameters: JSON.stringify(this.taskParameters),
					cornStatus: this.schedueForm.cornStatus,
					failurePolicy: this.schedueForm.failurePolicy,
					startDate: this.schedueForm.startDate,
					endDate: this.schedueForm.endDate,
					corn: this.schedueForm.corn,
					schedue: JSON.stringify(this.schedueForm)
				}).then(
					res => {
						this.$message({
							message: '任务保存成功!',
							type: 'success'
						});
						this.saveLoading = false;
					}).catch(err => {
					this.saveLoading = false;
				});
			},
			initBackup() {
				this.$refs['taskSettingRef'].initBackup();
			},
			submitJob() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				this.$refs['submitFromRef'].dialogVisible = true;
			},
			submitFlowTask(remarks) {
				this.submitLoading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/projectTaskFlow/submitFlowTask", {
					taskId: this.taskId,
					remarks: remarks
				}).then(
					res => {
						this.$message({
							message: '任务提交成功!',
							type: 'success'
						});
						this.submitLoading = false;
						this.initBackup();
					}).catch(err => {
					this.submitLoading = false;
				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			logCloseCallBack() {
				this.closeBottom();
			},
			jobRunCallBack() {
				this.isRun = false;
				this.runLoading = false;
			},
			closeDrawer() {

			},
			flowRunLog(nodes) {
				let flowPanel = this.$refs['flowPanel'];
				for (let i = 0; i < nodes.length; i++) {
					let item = nodes[i];
					if (item.nodeType != 99999 && item.nodeType != 0) {
						switch (item.status) {
							case -1: //失败
								flowPanel.updateNodeProperties(item.executeNodeId, {
									"icon": "el-icon-error",
									"iconColer": "#f56c6c",
									"nodeStatus": item.status
								});
								break;
							case 0: //成功
								flowPanel.updateNodeProperties(item.executeNodeId, {
									"icon": "el-icon-success",
									"iconColer": "#85ce61",
									"nodeStatus": item.status
								});
								break;
							case -20: //父级节点运行失败
								flowPanel.updateNodeProperties(item.executeNodeId, {
									"icon": "el-icon-warning",
									"iconColer": "#e6a23c",
									"nodeStatus": item.status
								});
								break;
							case 40: //停止
								flowPanel.updateNodeProperties(item.executeNodeId, {
									"icon": "el-icon-video-pause",
									"iconColer": "#FFEB3B",
									"nodeStatus": item.status
								});
								break;
							default:
								flowPanel.updateNodeProperties(item.executeNodeId, {
									"icon": "el-icon-loading",
									"iconColer": "#66b1ff",
									"nodeStatus": item.status
								});
						}
					}
				}
			}
		}
	}
</script>
<style scoped="scoped">
	.container {
		width: 100%;
	}
</style>