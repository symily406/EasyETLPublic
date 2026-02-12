<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>初始化部门</span>
						</div>
						<el-row>
							<el-col :span="8">
								<el-form-item label="机构全称" prop="initOrgFullName"
									:rules="[{required: true, message: '请输入机构全称', trigger: 'blur'},{min:1,max:128,message:'机构全称长度不能超过128个字符'}]">
									<el-input v-model="form.initOrgFullName" placeholder="请输入机构全称" size="small"
										maxlength="128">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="机构简称" prop="initOrgName"
									:rules="[{required: true, message: '请输入机构简称', trigger: 'blur'},{min:1,max:128,message:'机构简称长度不能超过128个字符'}]">
									<el-input v-model="form.initOrgName" placeholder="请输入机构简称" size="small"
										maxlength="128">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="机构编码" prop="initOrgCode"
									:rules="[{required: true, message: '请输入机构编码', trigger: 'blur'},{min:1,max:32,message:'机构编码长度不能超过32个字符'}]">
									<el-input v-model="form.initOrgCode" placeholder="请输入机构编码" size="small"
										maxlength="32">
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</el-card>
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>初始化角色</span>
						</div>
						<el-row>
							<el-col :span="12">
								<el-form-item label="角色编码" prop="initRoleCode"
									:rules="[{required: true, message: '请选择角色编码', trigger: 'blur'}]">
									<el-select v-model="form.initRoleCode" placeholder="角色编码" size="small"
										maxlength="32" style="width: 100%;">
										<el-option v-for="item in dicts" :key="item.value" :label="item.name"
											:value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="角色名称" prop="initRoleName"
									:rules="[{required: true, message: '请输入角色名称', trigger: 'blur'},{min:1,max:64,message:'角色名称长度不能超过64个字符'}]">
									<el-input v-model="form.initRoleName" placeholder="请输入角色名称" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</el-card>

					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>基础信息</span>
						</div>
						<el-row>
							<el-col :span="8">
								<el-form-item label="用户姓名" prop="initName">
									<el-input v-model="form.initName" placeholder="用户姓名" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="联系电话" prop="initPhone">
									<el-input v-model="form.initPhone" placeholder="联系电话" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="性别" prop="initSex">
									<el-radio-group v-model="form.initSex">
										<el-radio label="1">男</el-radio>
										<el-radio label="2">女</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
						</el-row>
					</el-card>
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>登陆信息</span>
						</div>
						<el-row>
							<el-col :span="12">
								<el-form-item label="登陆帐号" prop="initUserName"
									:rules="[{required: true, message: '请输入用登陆帐号', trigger: 'blur'},{min:5,max:24,message:'用登陆帐号长度在5到24个字符间'}]">
									<el-input v-model="form.initUserName" placeholder="请输入用登陆帐号" size="small"
										maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="密码" prop="inputPassword"
									:rules="[{required: true, message: '请输入密码', trigger: 'blur'},{min:6,max:12,message:'密码长度在6到12个字符间'}]">
									<el-input :type="passwordType" v-model="form.inputPassword" placeholder="请输入密码"
										size="small" maxlength="64">
										<i slot="suffix" class="el-icon-view" @click="showPassword"></i>
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="过期时间" prop="initExpireTime"
									:rules="[{required: true, message: '请选择过期时间', trigger: 'blur'}]">
									<el-date-picker v-model="form.initExpireTime" placeholder="请选择过期时间" size="small"
										maxlength="16" style="width:100%;" value-format="yyyy-MM-dd">
									</el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="确认密码" prop="inputConfirmPassword">
									<el-input :type="passwordType" v-model="form.inputConfirmPassword"
										placeholder="请输入确认密码" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>

					</el-card>
				</el-form>
			</div>
		</el-scrollbar>

		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>

</template>
<script>
	import { dialogMixin, dicts } from "@/mixins/mixins";
	export default {
		name: 'initCompany',
		components: {

		},
		props: {
			defaultProps: {
				children: 'children',
				label: 'name'
			}
		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				dicts: [],
				passwordType: 'password',
				form: {
					//企业编码
					companyId: '',
					//机构全称
					initOrgFullName: '',
					//机构简称
					initOrgName: '',
					//机构编码
					initOrgCode: '',
					//角色编码
					initRoleCode: '',
					//角色名称
					initRoleName: '',
					//用户名
					initUserName: '',
					//输入密码
					inputPassword:'',
					//密码
					initPassword: '',
					//确认密码
					inputConfirmPassword: '',
					//确认密码
					initConfirmPassword: '',
					//过期时间
					initExpireTime: '',
					initName: '',
					initPhone: '',
					initSex: '1',
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
				this.initRoleCodes();
			},
			closeWin() {
				this.dicts = [];
				this.form = this.$options.data().form;
				this.dialogVisible=false;
			},
			showPassword() {
				this.passwordType = this.passwordType == '' ? 'password' : '';
			},
			initRoleCodes() {
				this.initDict("role_code", res => {
					this.dicts = res.data;
				}, error => {

				});
			},
			
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.form.initPassword = this.$md5(this.form.inputPassword);
						this.form.initConfirmPassword = this.$md5(this.form.inputConfirmPassword);
						this.$ajax.post(this.getContentPath() + "/admin/system/company/initCompany", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.companyId == '' ? 1 : 2;
								this.$emit('eventCallBack', eventType);
								this.closeWin();
							}).catch(err => {
								this.loading = false;
							});
					} else {
						return false;
					}
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
