<template>
	<div class="log-container">
		<table border="0" width="100%" cellspacing="0" callpadding="0" v-show="showLogContainer">
			<tr>
				<td>
					<div>
						<el-tabs v-model="logActiveTab" type="border-card" @tab-remove="removeTaskJobResult"
							@tab-click="handleTabClick">
							<el-tab-pane label="日志" key="1234567890" name="1234567890" :closable="false">

							</el-tab-pane>
							<el-tab-pane :key="item.jobId" v-for="(item, index) in jobResultTabs" :label="item.title"
								:name="item.jobId" :closable="true">
							</el-tab-pane>
						</el-tabs>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<logContent ref="logContentRef" :logContainerHeight="logContainerHeight" v-show="showLogContent"
						:logInfo="logInfo" :isFlow="isFlow" @showLogInfo="showLogInfo" />
					<jobResult ref="jobResultRef" :logContainerHeight="logContainerHeight" v-show="showJobResult" />
				</td>
			</tr>
		</table>
		<div class="log-close">
			<a href="javascript:void(0);" title="关闭控制台" @click="logClose"><i class="el-icon-close"></i></a>
		</div>
		<eLogInfo ref="eLogInfoRef" dialogTitle="任务日志" dialogWidth="50vw" />
	</div>
</template>
<script>
	import jobResult from "./jobResult";
	import logContent from "./logContent";
	import {
		getRunTaskJob,
		setRunTaskJob
	} from "@/utils/localStore.js";
	import eLogInfo from '../../runTaskJob/module/logInfo';
	export default {
		name: 'log',
		components: {
			jobResult,
			eLogInfo,
			logContent
		},
		props: {
			showLogContainer: {
				type: Boolean,
				default: true
			},
			isFlow: {
				type: Boolean,
				default: false
			},
			bottomHeight: {
				type: Number,
				default: 0
			},
			headerHeight: {
				type: Number,
				default: 50
			}
		},
		mixins: [],
		data() {
			return {
				showLogContent: true,
				showJobResult: false,
				defaultLogActiveTab: '1234567890',
				logActiveTab: undefined,
				intervalId: null,
				initLogInfo: true,
				taskId: '',
				jobId: '',
				logInfo: [],
				jobResultTabs: [],
				jobResultTableHeader: [],
				jobResultTableData: [],
				taskType: 0,
				flowRunId: '',
				logContainerHeight: 0
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
			this.addLogInfo();
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
			bottomHeight(nval, oval) {
				if (nval == 0) {
					this.logContainerHeight = 0;
					return;
				}
				this.logContainerHeight = nval - this.headerHeight;
			}
		},
		methods: {
			addLogInfo() {
				// for (let i = 0; i < 100; i++) {
				// 	this.logInfo.push({
				// 		"logId": new Date().getTime(),
				// 		"jobId": 1,
				// 		"taskId": 1,
				// 		"logContent": "正在运行",
				// 		"status": 0,
				// 		"isEndNode": 0,
				// 		"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
				// 	});
				// }
			},
			showLogInfo(jobId) {
				this.$refs['eLogInfoRef'].jobId = jobId;
				this.$refs['eLogInfoRef'].dialogVisible = true;
			},
			startInterval() {
				let that = this;
				this.intervalId = setInterval(function() {
					if (that.isFlow) {
						that.initFlowRunLogInfo();
						return;
					}
					that.initRunLogInfo();
				}, 1000);
			},
			stopInterval() {
				clearInterval(this.intervalId);
			},
			initFlowRunLogInfo() {
				if (!this.initLogInfo) {
					return;
				}
				this.initLogInfo = false;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskFlow/queryFlowTaskRunStatus", {
						"flowRunId": this.flowRunId
					})
					.then(res => {
						this.initLogInfo = true;
						let nodes = res.data.nodes;
						this.$emit("flowRunLog", nodes);
						let showLogInfo = [];
						let startNode = nodes.filter(v => v.nodeType == 0)[0];
						let logItem = this.logInfo.filter(v => v.logId == startNode.jobRunNodeId)[0];
						if (!logItem) {
							showLogInfo.push({
								"logId": startNode.jobRunNodeId,
								"jobId": startNode.jobId,
								"taskId": this.taskId,
								"logContent": "开始执行",
								"status": startNode.status,
								"isEndNode": 0,
								"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
							});
						}
						nodes.forEach(item => {
							if (item.nodeType != 99999 && item.nodeType != 0) {
								let logId = this.$md5(item.jobRunNodeId + item.status);
								let logItem = this.logInfo.filter(v => v.logId == logId)[0];
								if (!logItem) {
									showLogInfo.push({
										"logId": logId,
										"jobId": item.jobId,
										"taskId": this.taskId,
										"logContent": item.desc,
										"status": item.status,
										"isEndNode": 0,
										"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
									});
								}
							}
						});
						let endNode = nodes.filter(v => v.nodeType == 99999)[0];
						if (endNode.status == 0 || endNode.status == -1 || endNode.status == 40) {
							let desc = "结束";
							switch (endNode.status) {
								case 0:
									desc += "[执行成功]";
									break;
								case -1:
									desc += "[执行失败]";
									break;
							}
							if (res.data) {
								desc += ",开始执行时间:" + res.data
									.executeTime;
							}
							if (res.completedTime) {
								desc += ",结束执行时间" + res.data.completedTime
							}
							if (res.usageTime) {
								desc += ",耗时:" + res.data.usageTime + "毫秒"
							}
							let logId = this.$md5(endNode.jobRunNodeId + endNode.status);
							showLogInfo.push({
								"logId": logId,
								"jobId": endNode.jobId,
								"taskId": this.taskId,
								"logContent": desc,
								"status": endNode.status,
								"isEndNode": 1,
								"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
							});
							this.stopInterval();
							this.$emit("jobRunCallBack");
						}
						this.logInfo = this.logInfo.concat(showLogInfo);
						this.scrollToBottom();
					}).catch(err => {
						this.initLogInfo = true;
						this.$emit("jobRunCallBack");
					});
			},
			initRunLogInfo() {
				if (!this.initLogInfo) {
					return;
				}
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJobLog/queryJobLogInfo", {
						"jobId": this.jobId
					})
					.then(res => {
						this.initLogInfo = true;
						let logInfo = this.logInfo;
						let job = res.data.job;
						let jobInfo = res.data.logInfo;

						let showLogInfo = [];
						jobInfo.forEach(item => {
							let logItem = logInfo.filter(v => v.logId == item.logId)[0];
							if (!logItem) {
								showLogInfo.push(item);
							}
						});
						// console.log(job.status);
						this.logInfo = this.logInfo.concat(showLogInfo);
						switch (job.status) {
							case 0:
								this.stopInterval();
								if (job.hasResult == 1) {
									this.initRunResult();
								}
								this.$emit("jobRunCallBack");
								break;
							case -1:
								this.stopInterval();
								this.$emit("jobRunCallBack");
								break;
							case 40:
								this.stopInterval();
								this.$emit("jobRunCallBack");
								break;
							case 10:
								// if (this.taskType == 2) {
								// 	this.logInfo.push({
								// 		"logId": new Date().getTime(),
								// 		"jobId": this.jobId,
								// 		"taskId": this.taskId,
								// 		"logContent": "正在运行",
								// 		"status": 0,
								// 		"isEndNode": 0,
								// 		"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
								// 	});
								// }
								break;
						}
						this.scrollToBottom();
					}).catch(err => {
						this.initLogInfo = true;
						this.$emit("jobRunCallBack");
					});
			},
			showLogContentSwitch() {
				this.logActiveTab = this.defaultLogActiveTab;
				this.showLogContent = true;
				this.showJobResult = false;
			},
			showJobResultSwitch() {
				this.showLogContent = false;
				this.showJobResult = true;
			},
			initRunResult() {
				this.logInfo.push({
					"logId": new Date().getTime(),
					"jobId": this.jobId,
					"taskId": this.taskId,
					"logContent": "正在获取运行结果",
					"status": 0,
					"isEndNode": 0,
					"addTime": this.$moment().format('YYYY-MM-DD HH:mm:ss')
				});
				this.initTaskJob(this.jobId);
				this.$refs['jobResultRef'].initJobResult(this.jobId);
			},
			handleTabClick(tab) {
				let tabName = tab.name;
				if (tabName == this.defaultLogActiveTab) {
					this.showLogContentSwitch();
				} else {
					this.showJobResultSwitch();
					this.$refs['jobResultRef'].initJobResult(tabName);
				}
			},
			handleTabs(jobId, data) {
				let runTaskJobObj = [];
				if (data && data.length > 0) {
					data.reverse().forEach((item, index) => {
						runTaskJobObj.push({
							taskId: item.taskId,
							jobId: item.jobId,
							title: "结果" + (index + 1),
							name: item.jobId,
							index: index
						});
					});
				}
				this.jobResultTabs = runTaskJobObj;
				this.showLogContentSwitch();
				if (jobId) {
					this.logActiveTab = jobId;
					this.showJobResultSwitch();
				}
			},
			initTaskJob(jobId) {
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJob/queryTaskJobByTaskId", {
						"taskId": this.taskId
					})
					.then(res => {
						this.handleTabs(jobId, res.data);
					}).catch(err => {

					});
			},
			removeTaskJobResult(jobId) {
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJob/removeTaskJobResult", {
						"taskId": this.taskId,
						"jobId": jobId
					})
					.then(res => {
						this.handleTabs(jobId, res.data);
						if (this.jobResultTabs.length > 0) {
							let lastJobId = this.jobResultTabs[this.jobResultTabs.length - 1];
							console.log(lastJobId);
							this.initTaskJob(lastJobId.jobId);
							this.$refs['jobResultRef'].initJobResult(lastJobId.jobId);
						} else {
							this.logActiveTab = this.defaultLogActiveTab;
							this.showJobResult = false;
							this.showLogContent = true;
						}
					}).catch(err => {

					});
			},
			logClose() {
				this.$emit("logCloseCallBack");
			},
			clearLog() {
				this.logInfo = [];
			},
			scrollToBottom() {
				let dom = document.getElementById("logContainer");
				dom.scrollTop = dom.scrollHeight;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .el-tabs__item {
		height: 30px;
		line-height: 30px;
	}

	::v-deep .el-tabs__content {
		display: none;
	}

	::v-deep .el-tabs--border-card {
		box-shadow: none;
	}
</style>