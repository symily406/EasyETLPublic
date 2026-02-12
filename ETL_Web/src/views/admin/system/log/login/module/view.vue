<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container">
				<el-form ref="form" :model="form" label-width="100px">
					<el-row>
						<el-col :span="12">
							<el-form-item label="名称" prop="name">
								{{form.name}}
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="请求方式" prop="mode">
								{{form.mode}}
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="IP地址" prop="ip">
								{{form.ip}}
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="操作时间" prop="addTime">
								{{form.addTime}}
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="用时" prop="usageTime">
								{{form.usageTime}}豪秒
							</el-form-item>
						</el-col>
					</el-row>
					<el-form-item label="请求地址" prop="url">
						{{form.url}}
					</el-form-item>
					<el-form-item label="操作方法" prop="controller">
						{{form.controller}}
					</el-form-item>

				</el-form>
			</div>
		</el-scrollbar>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'operationLogView',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				loading: false,
				dialogVisible: false,
				form: {
					logId: '',
					//名称
					name: '',
					//请求方式
					mode: '',
					//IP地址
					ip: '',
					//用时
					usageTime: '',
					//日志类型
					loginType: '',
					//启用
					enable: '',
					//显示排序
					showOrder: '',
					requestInfo: '',
					responseInfo: '',
					staffName:'',
					addTime:'',
					url:'',
					controller:''
				}
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
				this.initData();
			},
			closeWin() {
				this.form = this.$options.data().form;
				this.dialogVisible = false;
			},
			initData() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/operationLog/getById", { logId: this.form.logId })
					.then(res => {
						this.form = res.data;
					}).catch(err => {

					});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
