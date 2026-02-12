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
								<el-form-item label="归属企业" prop="companyId">
									{{form.companyName}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="归属部门" prop="orgId">
									{{form.orgName}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="用户姓名" prop="name" :rules="[{required: true, message: '用户姓名不能为空', trigger: 'blur'}]">
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
								<el-form-item label="登陆帐号" prop="userName">
									{{form.userName}}
									<el-link type="primary" style="line-height: 20px;" @click="modifyPassword">修改密码
									</el-link>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="过期时间" prop="expireTime"
									:rules="[{required: true, message: '请选择过期时间', trigger: 'blur'}]">
									<el-date-picker v-model="form.expireTime" placeholder="请选择过期时间" size="small"
										maxlength="16" style="width:100%;" value-format="yyyy-MM-dd">
									</el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="角色" prop="userRoles"
									:rules="[{required: false, message: '请选择角色', trigger: 'blur'}]">
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
								<el-form-item label="用户状态" prop="lock">
									<el-switch v-model="form.lock" active-color="#ff4949" inactive-color="#13ce66"
										size="small" inactive-value="0" active-value="1" inactive-text="正常"
										active-text="锁定"></el-switch>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单点登陆" prop="isSinglePointLogin">
									<el-switch v-model="form.singlePointLogin" active-color="#ff4949" inactive-color="#13ce66"
										size="small" inactive-value="0" active-value="1" inactive-text="关闭"
										active-text="开启"></el-switch>
								</el-form-item>
							</el-col>
						</el-row>
						
					</el-card>
				</el-form>
			</div>
		</el-scrollbar>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
		<modifyPassword ref="modifyPassword" dialogTitle="修改密码" dialogWidth="500px" dialogHeight="200px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</el-dialog>
</template>
<script>
	import modifyPassword from './modify_password';
	export default {
		name: 'staffForm',
		components: {
			modifyPassword
		},
		props: {
			dialogTop: {
				type: String,
				default: "10px"
			},
			dialogTitle: {
				type: String,
				default: '添加'
			},
			dialogWidth: {
				type: String,
				default: '50%'
			},
			dialogHeight: {
				type: String,
				default: window.innerHeight - 160 + "px"
			}
		},
		data() {
			return {
				rules: {

				},
				loading: false,
				dialogVisible: false,
				companys: [],
				orgs: [],
				dutys: [],
				dutyIds: [],
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
					//登陆次数
					loginNum: 1,
					//锁定
					isLock: '0',
					lock: '0',
					isSinglePointLogin: '0',
					singlePointLogin:'0',
					//过期时间
					expireTime: '',
					//归属企业
					companyId: '',
					companyName: '',
					//归属机构
					orgId: '',
					orgName: '',
					dutyIds: '',
					name: '',
					phone: '',
					sex: '1',
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
				this.initRoles();
				this.initUserRoles();
				this.initDuty();
				this.initStaffDuty();
			},
			closeWin() {
				//富文本编辑器
				this.dialogVisible = false;
				this.form = this.$options.data().form;
				this.userRoles = [];
				this.dutyIds = [];
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 20: //修改
						this.$message({
							message: '密码修改成功!',
							type: 'success'
						});
						break;
				}
			},
			modifyPassword() {
				const modifyPassword = this.$refs['modifyPassword'];
				modifyPassword.form.staffId = this.form.staffId;
				modifyPassword.dialogVisible = true;
			},
			initUserRoles() {
				if (this.form.staffId) {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/staff/queryRoles", { staffId: this.form.staffId })
						.then(res => {
							res.data.forEach(item => {
								this.userRoles.push(item.roleId);
							});
							this.form.userRoles=this.userRoles;
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
			initStaffDuty() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/staffDuty/queryDutys", { "staffId": this.form.staffId })
					.then(res => {
						res.data.forEach(item => {
							this.dutyIds.push(item.dutyId);
						});
						this.form.dutys=this.dutyIds;
					}).catch(err => {

					});
			},
			initRoles() {
				this.$ajax.get(this.getContentPath() + "/admin/system/role/queryOrgRoles", {
						"orgId": this.form.orgId,
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
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.form.roles = this.form.userRoles.join(",");
						this.form.isLock = this.form.lock;
						this.form.dutyIds = this.form.dutys.join(",");
						this.form.isSinglePointLogin=this.form.singlePointLogin;
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
