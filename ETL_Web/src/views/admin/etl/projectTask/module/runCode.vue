<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body class="createTableDialog">
		<div class="form-container" v-loading="loading" :style="{height:dialogHeight}">
			<codeEditor @change="codeChange" v-model="sqlCode" language="sql"></codeEditor>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit()">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import codeEditor from "@/components/monaco/monaco.vue";
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'runCode',
		components: {
			codeEditor
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				sqlCode: '',
				taskId: '',
				projectId: '',
				projectWorkSpaceLayerRelId: '',
				projectWorkSpaceId: '',
				workSpaceType: '',
				scriptContent: '',
				dbType: '',
				dbSourceId: ''
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
			openWin() {

			},
			closeWin() {
				this.dialogVisible = false;
				this.sqlCode = '';
				this.taskId = '';
				this.projectId = '';
				this.projectWorkSpaceLayerRelId = '';
				this.projectWorkSpaceId = '';
				this.workSpaceType = '';
				this.scriptContent = '';
				this.dbType = '';
				this.dbSourceId = '';
			},
			codeChange(code) {
				this.scriptContent = code;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit() {
				this.loading = true;
				let params = {
					"taskId": this.taskId,
					"dbType": this.dbType,
					"dbSourceId": this.dbSourceId,
					"scriptContent": this.scriptContent
				};
				this.$ajax.post(this.getContentPath() + "/admin/etl/runTaskJob/saveOrUpdate", params).then(
					res => {
						this.loading = false;
						let message = "任务运行成功！";
						if (res.data.isOldRunner) {
							message = "任务正在运行！";
						}
						this.runLoading = true;
						this.$message({
							message: message,
							type: 'success'
						});
						this.$emit("runCodeCallBack", res.data.jobId);
						this.closeWin();
					}).catch(err => {
					this.loading = false;
				});
			}
		}
	}
</script>
<style scoped="scoped">
	.createTableDialog {
		.el-dialog__body {
			padding: 0px;
		}
	}
</style>