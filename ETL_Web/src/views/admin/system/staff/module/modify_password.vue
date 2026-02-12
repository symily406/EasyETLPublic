<template>
	<el-dialog <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" append-to-body
		:close-on-click-modal="false" :width="dialogWidth" @opened="openWin" @close="closeWin">
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="密码" prop="password"
					:rules="[{required: true, message: '请输入密码', trigger: 'blur'},{min:6,max:12,message:'密码长度在6到12个字符间'}]">
					<el-input :type="passwordType" v-model="form.password" placeholder="请输入密码" size="small"
						maxlength="64">
						<i slot="suffix" class="el-icon-view" @click="showPassword"></i>
					</el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="confirmPassword">
					<el-input :type="passwordType" v-model="form.confirmPassword" placeholder="请输入确认密码" size="small"
						maxlength="64">
					</el-input>
				</el-form-item>
			</el-form>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>

	</el-dialog>
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'staffForm',
		components: {
			
		},
		props: {
			
		},
		mixins: [dialogMixin],
		data() {
			//确认密码验证
			let confirmPassword = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请再次输入密码'));
				} else if (value !== this.form.password) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				rules: {
					confirmPassword: [
						{ required: true, validator: confirmPassword, trigger: 'blur' }
					]
				},
				passwordType: 'password',
				companys: [],
				orgs: [],
				defaultProps: {
					children: 'children',
					label: 'label'
				},
				form: {
					//主键
					staffId: '',
					//密码,
					password: '',
					//确认密码
					confirmPassword: ''
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

			},
			closeWin() {
				//富文本编辑器
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			showPassword() {
				this.passwordType = this.passwordType == '' ? 'password' : '';
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form.password=this.$md5(this.form.password);
						this.form.confirmPassword=this.$md5(this.form.confirmPassword);
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/staff/updatePassowrd", this.form)
							.then(res => {
								this.loading = false;
								const eventType =20;
								this.$emit('eventCallBack', eventType);
								this.closeWin();
							}).catch(err => {
								this.loading = false;
							});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>
