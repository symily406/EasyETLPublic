<template>
	<div class="flow-main-container bg-white">
		<table border="0" cellspacing="0" callpadding="0" width="100%">
			<tr>
				<td class="flow-task-run">
					<table border="0" cellspacing="0" callpadding="0">
						<tr>
							<td width="20">&nbsp;</td>
							<td width="50"><el-button type="text" icon="el-icon-video-play" class="button-padding-none"
									:disabled="isRun==1?true:false" @click="flowRun">运行</el-button></td>
							<td width="20">&nbsp;&nbsp;</td>
							<td width="50"><el-button type="text" icon="el-icon-video-pause" class="button-padding-none"
									:disabled="isRun==1?false:true" @click="flowStop">停止</el-button></td>
							<td width="20">&nbsp;&nbsp;</td>
							<td width="50"><el-button type="text" icon="fa fa-save" class="button-padding-none cblack" @click="submitFlow">
									保存</el-button></td>
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
									:height="flowContainerHeight" :textEdit="false"></flowPanel>
							</td>
							<td width="40" valign="top" :style="{height:flowContainerHeight+'px'}">
								<flowSetting />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<flowTaskList ref="flowTaskList" dialogTitle="任务选择" dialogWidth="1000px" dialogHeight="400"
			:dialogTop="this.$store.state.global.dialogTop" @taskCallBack="taskCallBack"></flowTaskList>
	</div>
</template>

<script>
	import flowPanel from "@/components/logicflow/FlowPanel";
	import flowTaskList from "./module/flowTaskList";
	import flowSetting from './module/flowSetting';
	export default {
		name: "projectFlow",
		components: {
			flowPanel,
			flowTaskList,
			flowSetting
		},
		props: {

		},
		mixins: [],
		data() {
			return {
				rules: {},
				loading: false,
				isRun: 0,
				//流程
				lf: null,
				flowContainerHeight: 600,
				nodeId: null
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

		},
		methods: {
			initContentHeight() {
				this.$nextTick(() => {
					this.flowContainerHeight = window.innerHeight;
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
					"workSpaceType": row.workSpaceTypeworkSpaceType,
					"taskFolderId": row.taskFolderId,
					"text": row.taskName,
				});
				this.$refs['flowTaskList'].dialogVisible = false;
			},
			flowRun() {

			},
			flowStop() {

			},
			submitFlow(){
				this.$refs['flowPanel'].getFLowNodes();
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.container {
		width: 100%;
	}
</style>