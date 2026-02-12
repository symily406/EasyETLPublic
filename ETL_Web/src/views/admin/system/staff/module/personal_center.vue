<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="staffVo" label-width="100px" :rules="rules">

					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>基础信息</span>
						</div>
						<el-row>
							<el-col :span="12">
								<el-form-item label="归属企业" prop="companyId">
									{{staffVo.companyName}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="归属部门" prop="orgId">
									{{staffVo.orgName}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="用户姓名" prop="name" :rules="[{required: true, message: '用户姓名不能为空', trigger: 'blur'}]">
									<el-input v-model="staffVo.name" placeholder="用户姓名" size="small" maxlength="64">
									</el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="岗位">
									{{dutyNames}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="性别" prop="sex">
									<el-radio-group v-model="staffVo.sex">
										<el-radio label="1">男</el-radio>
										<el-radio label="2">女</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系电话" prop="phone">
									<el-input v-model="staffVo.phone" placeholder="联系电话" size="small" maxlength="64">
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
									{{staffVo.userName}}
									<el-link type="primary" style="line-height: 20px;" @click="modifyPassword">修改密码
									</el-link>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="过期时间" prop="expireTime">
									{{staffVo.expireTime}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="角色">
									{{roleNames}}
								</el-form-item>
							</el-col>

						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="用户状态" prop="lock">
									{{staffVo.isLock==0?'正常':'锁定'}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单点登陆" prop="isSinglePointLogin">
									{{staffVo.isSinglePointLogin==0?'关闭':'开启'}}
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
				staffVo: {
					staffId: '',
					userName: '',
					companyName: '',
					orgName: '',
					lock: '',
					isLock:0,
					name: '',
					phone: '',
					sex: '1',
					isSinglePointLogin: 0,
					roles:'',
					dutyIds:''
				},
				roleNames:'',
				dutyNames:''
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
				this.queryPersonalCenterStaff();
			},
			closeWin() {
				this.dialogVisible = false;
				this.staffVo = this.$options.data().staffVo;
			},
			queryPersonalCenterStaff() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/staff/queryPersonalCenterStaff", {})
					.then(res => {
						this.staffVo = res.data.staffVo;
						
						let dutyNames='';
						let dutys='';
						res.data.staffDutyVos.forEach(item=>{
							if(dutyNames){
								dutyNames+=",";
								dutys+=",";
							}
							dutyNames+=item.dutyName;
							dutys+=item.dutyId;
						});
						this.dutyNames=dutyNames;
						this.staffVo.dutyIds=dutys;
						
						let roleNames='';
						let roles='';
						res.data.staffRoleVos.forEach(item=>{
							if(roleNames){
								roleNames+=",";
								roles+=",";
							}
							roleNames+=item.name;
							roles+=item.roleId;
						})
						this.roleNames=roleNames;
						this.staffVo.roles=roles;
					}).catch(err => {

					});
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
				modifyPassword.form.staffId = this.staffVo.staffId;
				modifyPassword.dialogVisible = true;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/staff/saveOrUpdate", this.staffVo)
							.then(res => {
								this.loading = false;
								this.$emit('eventCallBack', 1);
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
