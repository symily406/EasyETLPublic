<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>基础信息</span>
						</div>
						<el-row>
							<el-col :span="12">
								<el-form-item label="归属企业" prop="companyId"
									:rules="[{required: true, message: '请选择归属企业', trigger: 'blur'}]">
									<el-select v-model="form.companyId" placeholder="归属企业" size="small" maxlength="32"
										style="width: 100%;" @change="initOrg">
										<el-option v-for="item in companys" :key="item.companyId"
											:label="item.companyName" :value="item.companyId"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="归属部门" prop="orgId"
									:rules="[{required: true, message: '请选择归属部门', trigger: 'blur'}]" class="treeSelect"
									style="margin-bottom: 5px;">
									<treeSelect v-model="orgId" placeholder="归属部门" :multiple="false"
										noOptionsText="请选择归属企业" :options="orgs" @select="orgSelectEvent" />
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="用户姓名" prop="name">
									<el-input v-model="form.name" placeholder="用户姓名" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="岗位" prop="dutys"
									:rules="[{required: true, message: '请选择岗位', trigger: 'blur'}]">
									<el-select v-model="form.dutys" placeholder="岗位" size="small" maxlength="32"
										style="width: 100%;" multiple>
										<el-option v-for="item in dutys" :key="item.dutyIds" :label="item.dutyName"
											:value="item.dutyId"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="性别" prop="sex">
									<el-radio-group v-model="form.sex">
										<el-radio label="1">男</el-radio>
										<el-radio label="2">女</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系电话" prop="phone">
									<el-input v-model="form.phone" placeholder="联系电话" size="small" maxlength="64">
									</el-input>
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
								<el-form-item label="登陆帐号" prop="userName"
									:rules="[{required: true, message: '请输入用登陆帐号', trigger: 'blur'},{min:5,max:24,message:'用登陆帐号长度在5到24个字符间'}]">
									<el-input v-model="form.userName" placeholder="请输入用登陆帐号" size="small"
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
								<el-form-item label="过期时间" prop="expireTime"
									:rules="[{required: true, message: '请选择过期时间', trigger: 'blur'}]">
									<el-date-picker v-model="form.expireTime" placeholder="请选择过期时间" size="small"
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
						<el-row>
							<el-col :span="12">
								<el-form-item label="角色" prop="userRoles"
									:rules="[{required: true, message: '请选择角色', trigger: 'blur'}]">
									<el-select v-model="form.userRoles" placeholder="角色" size="small" maxlength="32"
										style="width: 100%;" multiple>
										<el-option v-for="item in roles" :key="item.rId" :label="item.name"
											:value="item.rId"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="排序" prop="showOrder"
									:rules="[{required: true, message: '请输入排序', trigger: 'blur'}]"
									class="el-input-number-left">
									<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1"
										:max="999999" :precision="0" controls-position="right"
										class="max-width-100-ratio">
									</el-input-number>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="用户状态" prop="isLock">
									<el-switch v-model="form.isLock" active-color="#ff4949" inactive-color="#13ce66"
										size="small" inactive-value="0" active-value="1" inactive-text="正常"
										active-text="锁定"></el-switch>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单点登陆" prop="isSinglePointLogin">
									<el-switch v-model="form.isSinglePointLogin" active-color="#ff4949" inactive-color="#13ce66"
										size="small" inactive-value="0" active-value="1" inactive-text="关闭"
										active-text="开启"></el-switch>
								</el-form-item>
							</el-col>
						</el-row>
					</el-card>

					<el-form-item label="配置" v-show="false">
						<el-checkbox v-model="form.allowEdit" :true-label="1" :false-label="0" label="可修改">
						</el-checkbox>
						<el-checkbox v-model="form.allowDelete" :true-label="1" :false-label="0" label="可删除">
						</el-checkbox>
						<el-checkbox v-model="form.enable" :true-label="1" :false-label="0" label="启用">
						</el-checkbox>

					</el-form-item>
				</el-form>
			</div>
		</el-scrollbar>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	import treeSelect from '@riophae/vue-treeselect'
	export default {
		name: 'staffForm',
		components: {
			treeSelect
		},
		props: {

		},
		watch: {
			orgId: {
				handler() {
					if (!this.orgId) {
						this.orgSelectClear();
					}
				}
			}
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
				dutys: [],
				dutyIds: [],
				passwordType: 'password',
				companys: [],
				orgs: [],
				orgId: null,
				defaultProps: {
					children: 'children',
					label: 'label'
				},
				roles: {},
				userRoles: [],
				roleShow: false,
				form: {
					//主键
					staffId: '',
					//用户名
					userName: '',
					//输入密码
					inputPassword: '',
					//密码
					password: '',
					//确认密码
					inputConfirmPassword: '',
					//确认密码
					confirmPassword: '',
					//登陆次数
					loginNum: 1,
					//锁定
					isLock: '0',
					//过期时间
					expireTime: '',
					//归属企业
					companyId: '',
					//归属机构
					orgId: null,
					roles: '',
					dutyIds: '',
					name: '',
					phone: '',
					sex: '1',
					isSinglePointLogin: '0',

					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1,
					userRoles: [],
					dutys:[]
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
				//富文本编辑器
				//this.$refs['formContent'].$emit("initTinymce");
				this.initCompany();
				this.initOrg();
				this.initDuty();
			},
			closeWin() {
				//富文本编辑器
				//this.$refs['formContent'].$emit("destroyTinymce");
				this.dialogVisible = false;
				this.form = this.$options.data().form;
				this.orgId = null;
				this.dutyIds = [];
				this.orgSelectClear();
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			initCompany() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/company/queryCompanyDropDown", {})
					.then(res => {
						this.companys = res.data;
					}).catch(err => {

					});
			},
			initOrg() {
				if (this.form.companyId) {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/org/queryOrgDropDown", { companyId: this.form.companyId })
						.then(res => {
							this.form.orgId = null;
							this.orgId = null;
							this.orgs = res.data;
						}).catch(err => {

						});
				}
			},
			initDuty() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/duty/queryDutys", {})
					.then(res => {
						this.dutys = res.data;
					}).catch(err => {

					});
			},
			orgSelectClear() {
				this.roles = [];
				this.roleShow = false;
				this.form.role = null;
			},
			orgSelectEvent(node, id) {
				this.orgId = node.id;
				if (this.orgId) {
					this.form.orgId = this.orgId;
					this.initRoles();
				}
			},
			initRoles() {
				this.$ajax.get(this.getContentPath() + "/admin/system/role/queryOrgRoles", {
						"orgId": this.form
							.orgId,
						"companyId": this.form.companyId
					})
					.then(res => {
						this.roles = res.data;
						if (this.roles.length > 0) {
							this.roleShow = true;
						}
					}).catch(err => {
						this.loading = false;
					});
			},
			showPassword() {
				this.passwordType = this.passwordType == '' ? 'password' : '';
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form.password = this.$md5(this.form.inputPassword);
						this.form.confirmPassword = this.$md5(this.form.inputConfirmPassword);
						this.form.roles = this.form.userRoles.join(",");
						this.form.dutyIds = this.form.dutys.join(",");
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/staff/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.staffId == '' ? 1 : 2;
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
