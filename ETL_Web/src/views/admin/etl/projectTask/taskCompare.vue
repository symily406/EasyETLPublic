<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" width="100%"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body class="compare-container"
		v-loading="loading">
		<table border="0" cellspacing="0" callpadding="0" width="100%">
			<tr>
				<td width="50%" class="iframe-border">
					<iframe :src="currentUrl" frameborder="0" scrolling="auto" width="100%"
						class="iframe-container"></iframe>
				</td>
				<td width="50%" class="iframe-border">
					<iframe :src="compareUrl" frameborder="0" scrolling="auto" class="iframe-container"></iframe>
				</td>
			</tr>
		</table>
		<div class="tcenter">
			<span>
				<el-button type="primary" size="small" @click="rollBack">回滚当前版本</el-button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<el-button size="small" @click="closeWin">关 闭</el-button>
			</span>
		</div>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'dataxTaskCompare',
		components: {},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				taskInfo: undefined,
				loading: false,
				dialogVisible: false,
				currentUrl: undefined,
				compareUrl: undefined
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
		methods: {
			init(taskInfo) {
				let url = "";
				switch (parseInt(taskInfo.workSpaceType)) {
					case 1:
						url = "#/workSpace/task/dataxTask";
						break;
					case 2:
						url = "#/workSpace/task/sqlTask";
						break;
					case 5:
						url = "#/workSpace/task/flowTask";
						break;
				};
				this.taskInfo = taskInfo;
				let param = "projectId=" + taskInfo.projectId +
					"&projectWorkSpaceLayerRelId=" + taskInfo.projectWorkSpaceLayerRelId + "&workSpaceType=" + taskInfo
					.workSpaceType + "&compare=true";
				this.currentUrl = url + "?" + param + "&taskId=" + taskInfo.backupTaskId;
				this.compareUrl = url + "?" + param + "&taskId=" + taskInfo.taskId + "&backup=true";
			},
			openWin() {

			},
			closeWin() {
				this.dialogVisible = false;
				this.currentUrl = undefined;
				this.compareUrl = undefined;
			},
			rollBack() {
				this.$confirm('您是否要回滚当前版本吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.loading = true;
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskBackup/rollBack", {
						"taskId": this.taskInfo.taskId
					}).then(
						res => {
							this.$message({
								message: '版本回滚成功!!',
								type: 'success'
							});
							this.$emit("rollBack", this.taskInfo);
							this.closeWin();
							this.loading = false;
						}).catch(err => {
						this.loading = false;
					});
				}).catch(() => {

				});

			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .el-dialog__body {
		padding: 0px;
		padding-bottom: 1rem;
	}

	.compare-container {
		overflow: hidden !important;

		.iframe-border {
			border: solid 1px #ccc;
		}
	}

	.tcenter {
		margin-top: 10px;
	}

	.iframe-container {
		width: 100%;
		height: 85vh;
	}
</style>