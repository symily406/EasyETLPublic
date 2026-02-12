<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="120px" :rules="rules">
				<el-form-item label="数据源类型" prop="sourceTypeName"
					:rules="[{required: true, message: '请输入数据源名称', trigger: 'blur'}]">
					<el-input v-model="form.sourceTypeName" size="small" maxlength="64" disabled></el-input>
				</el-form-item>
				<el-form-item label="数据源名称" prop="dbSourceName"
					:rules="[{required: true, message: '请输入数据源名称', trigger: 'blur'}]">
					<el-input v-model="form.dbSourceName" placeholder="请输入数据源名称" size="small" maxlength="64"
						disabled></el-input>
				</el-form-item>
				<el-form-item label="描述" prop="description"
					:rules="[{required: false, message: '请输入数据源描述', trigger: 'blur'},{min:1,max:512,message:'描述长度不能超过512个字符'}]">
					<el-input type="textarea" rows="3" v-model="form.description" placeholder="请输入数据源描述" size="small"
						maxlength="512" clearable></el-input>
				</el-form-item>
				<el-form-item label="JDBC_URL" prop="jdbcUrl"
					:rules="[{required: true, message: '请输入JDBC_URL', trigger: 'blur'},{min:1,max:512,message:'JDBC_URL长度不能超过512个字符'}]">
					<el-input type="textarea" rows="3" v-model="form.jdbcUrl" placeholder="请输入JDBC_URL" size="small"
						maxlength="512" clearable></el-input>
				</el-form-item>
				<el-form-item label="驱动" prop="jdbcDrive"
					:rules="[{required: true, message: '请输入驱动', trigger: 'blur'},{min:1,max:64,message:'驱动长度不能超过64个字符'}]">
					<el-input v-model="form.jdbcDrive" placeholder="请输入驱动" size="small" maxlength="64"
						clearable></el-input>
				</el-form-item>
				<el-row v-if="form.isAccount==1||form.isPassword==1">
					<el-col :span="12" v-if="form.isAccount==1"><el-form-item label="用户名" prop="userName"
							:rules="[{required: true, message: '请输入用户名', trigger: 'blur'},{min:1,max:64,message:'用户名长度不能超过64个字符'}]">
							<el-input v-model="form.userName" placeholder="请输入用户名" size="small" maxlength="64"
								clearable></el-input>
						</el-form-item></el-col>
					<el-col :span="12" v-if="form.isPassword==1"><el-form-item label="密码" prop="password"
							:rules="[{required: true, message: '请输入密码', trigger: 'blur'},{min:1,max:1024,message:'密码长度不能超过1024个字符'}]">
							<el-input type="password" v-model="form.password" placeholder="请输入密码" size="small"
								maxlength="1024" clearable></el-input>
						</el-form-item></el-col>
				</el-row>
				<el-row v-if="form.isCatalog==1||form.isSchema==1">
					<el-col :span="12" v-if="form.isCatalog==1"><el-form-item label="catalog" prop="dbCatalog"
							:rules="[{required: true, message: '请选择catalog', trigger: 'blur'}]">
							<table width="100%" border="0" class="task-table" cellspacing="0" callpadding="0">
								<tr>
									<td><el-select v-model="form.dbCatalog" placeholder="catalog" clearable size="small"
											class="max-width-100-ratio" filterable>
											<el-option v-for="item in dbCatalogs" :key="item.catalog"
												:label="item.catalog" :value="item.catalog">
											</el-option>
										</el-select></td>
									<td>
										<el-button type="success" size="small"
											@click="loadDbCatalog('form')">载入</el-button>
									</td>
								</tr>
							</table>

						</el-form-item></el-col>
					<el-col :span="12" v-if="form.isSchema==1"><el-form-item label="schema" prop="dbSchema"
							:rules="[{required: true, message: '请选择schema', trigger: 'blur'}]">
							<table width="100%" border="0" class="task-table" cellspacing="0" callpadding="0">
								<tr>
									<td>
										<el-select v-model="form.dbSchema" placeholder="schema" clearable size="small"
											class="max-width-100-ratio" filterable>
											<el-option v-for="item in dbSchemas" :key="item.schema" :label="item.schema"
												:value="item.schema">
											</el-option>
										</el-select>
									</td>
									<td>
										<el-button type="success" size="small"
											@click="loadDbSchema('form')">载入</el-button>
									</td>
								</tr>
							</table>
						</el-form-item></el-col>
				</el-row>
				<el-form-item label="文件系统" prop="defaultFs" v-show="form.sourceType==2"
					:rules="[{required: form.sourceType==2?true:false, message: '请输入文件系统', trigger: 'blur'},{min:1,max:64,message:'文件系统长度不能超过64个字符'}]">
					<el-input v-model="form.defaultFs" placeholder="请输入文件系统" size="small" maxlength="64"
						clearable></el-input>
				</el-form-item>
				<el-row>
					<el-col :span="4"><el-form-item label="Kerberos认证" prop="isKerberos" v-show="form.sourceType==2">
							<el-switch v-model="form.haveKerberos" active-color="#13ce66" inactive-color="#ff4949"
								:active-value="1" :inactive-value="0">
							</el-switch>
						</el-form-item></el-col>
					<el-col :span="10"><el-form-item label="Kerberos路径" prop="kerberosKeytabFilePath"
							v-show="form.sourceType==2"
							:rules="[{required: form.haveKerberos==1?true:false, message: '请输入Kerberos路径', trigger: 'blur'},{min:1,max:128,message:'Kerberos路径长度不能超过128个字符'}]">
							<el-input v-model="form.kerberosKeytabFilePath" placeholder="请输入Kerberos路径" size="small"
								maxlength="128" clearable></el-input>
						</el-form-item></el-col>
					<el-col :span="10"><el-form-item label="Principal" prop="kerberosPrincipal"
							v-show="form.sourceType==2"
							:rules="[{required: form.haveKerberos==1?true:false, message: '请输入Principal', trigger: 'blur'},{min:1,max:128,message:'Principal长度不能超过128个字符'}]">
							<el-input v-model="form.kerberosPrincipal" placeholder="请输入Principal" size="small"
								maxlength="128" clearable></el-input>
						</el-form-item></el-col>
				</el-row>
			</el-form>
		</div>
		<div class="tcenter">
			<span>
				<el-button size="small" type="danger" icon="el-icon-refresh"
					@click="testConnect('form')">测式连接</el-button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
			</span>
		</div>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin,
		dicts
	} from "@/mixins/mixins";
	export default {
		name: 'dbSourceForm',
		components: {},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				fileSystemCheck: false,
				kerberosCheck: false,
				dbSourceType: [],
				dbDialects: [],
				dbCatalogs: [],
				dbSchemas: [],
				form: {
					isCatalog: 0,
					isSchema: 0,
					isAccount: 0,
					isPassword: 0,
					sourceTypeName: undefined,
					sourceType: undefined,
					dbSourceId: undefined,
					//数据源类型
					sourceTypeId: undefined,
					//数据源名称
					dbSourceName: undefined,
					//JDBC_URL
					jdbcUrl: undefined,
					//驱动
					jdbcDrive: undefined,
					//用户名
					userName: '',
					//密码
					password: '',
					//加密盐值
					salt: undefined,
					dbCatalog: '',
					dbSchema: '',
					//描述
					description: undefined,
					//文件系统
					defaultFs: '',
					//是否有Kerberos认证
					haveKerberos: 0,
					//keytab文件路径
					kerberosKeytabFilePath: '',
					//Kerberos认证Principal名
					kerberosPrincipal: '',
					//文件类型
					fileType: undefined,
					//是否能连接
					isConn: 0,
					//引用次数
					quoteNum: undefined,
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1
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
				let that = this;
				this.initDict("DB_DIALECT", function(res) {
					that.dbDialects = res.data;
				}, function(error) {

				});
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			loadDbCatalog(formName) {
				if (!this.form.jdbcUrl) {
					this.$message.error('请输入JDBC_URL');
					return;
				}
				if (!this.form.jdbcDrive) {
					this.$message.error('请输入驱动');
					return;
				}
				if (!this.form.userName && this.form.isAccount == 1) {
					this.$message.error('请输入用户名');
					return;
				}
				if (!this.form.password && this.form.isPassword == 1) {
					this.$message.error('请输入密码');
					return;
				}
				this.$ajax.post(this.getContentPath() + "/admin/etl/dbSource/queryDbSourcesDbCatalog", this
					.form).then(
					res => {
						this.$message({
							message: 'catalog载入成功',
							type: 'success'
						});
						this.dbCatalogs = res.data;
					}).catch(err => {

				});
			},
			loadDbSchema(formName) {
				if (!this.form.jdbcUrl) {
					this.$message.error('请输入JDBC_URL');
					return;
				}
				if (!this.form.jdbcDrive) {
					this.$message.error('请输入驱动');
					return;
				}
				if (!this.form.userName && this.form.isAccount == 1) {
					this.$message.error('请输入用户名');
					return;
				}
				if (!this.form.password && this.form.isPassword == 1) {
					this.$message.error('请输入密码');
					return;
				}
				if (!this.form.dbCatalog && this.form.isCatalog == 1) {
					this.$message.error('请选择catalog');
					return;
				}
				this.$ajax.post(this.getContentPath() + "/admin/etl/dbSource/queryDbSourcesDbSchema", this
					.form).then(
					res => {
						this.$message({
							message: 'schemas载入成功',
							type: 'success'
						});
						this.dbSchemas = res.data;
					}).catch(err => {

				});
			},
			testConnect(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$ajax.post(this.getContentPath() + "/admin/etl/dbSource/testConnect", this.form).then(
							res => {
								if (res.data.connect) {
									this.$message({
										message: res.data.message,
										type: 'success'
									});
									this.form.isConn = 1;
									return;
								}
								this.$message.error(res.data.message);
							}).catch(err => {

						});
					} else {
						return false;
					}
				});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			onSubmit(formName) {
				console.log(this.form.isConn);
				if (this.form.isConn == 0) {
					this.$message.error('请先测式数据库连接,测式通过后才能保存');
					return;
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (this.form.isAccount != 1) {
							this.form.userName = '';
						}
						if (this.form.isPassword != 1) {
							this.form.password = '';
						}
						if (this.form.isCatalog != 1) {
							this.form.dbCatalog = '';
						}
						if (this.form.isSchema != 1) {
							this.form.dbSchema = '';
						}

						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/dbSource/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.dbSourceId = 2;
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
<style scoped="scoped">

</style>