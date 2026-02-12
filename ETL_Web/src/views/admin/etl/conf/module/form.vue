<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<table border="0" cellspacing="0" callpadding="0" width="100%" style="height: 100%;">
			<tr>
				<td width="20%">
					<el-menu default-active="1" v-model="activeIndex" class="el-menu-vertical-demo"
						@select="handleMenuSelect">
						<el-menu-item index="1">
							<i class="el-icon-menu"></i>
							<span slot="title">基础配置</span>
						</el-menu-item>
						<el-menu-item index="2">
							<i class="el-icon-menu"></i>
							<span slot="title">Yarn配置</span>
						</el-menu-item>
						<el-menu-item index="3">
							<i class="el-icon-menu"></i>
							<span slot="title">Datax配置</span>
						</el-menu-item>
						<el-menu-item index="4">
							<i class="el-icon-menu"></i>
							<span slot="title">Sql执行器</span>
						</el-menu-item>
					</el-menu>
				</td>
				<td width="80%" valign="top">
					<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
						<el-form ref="form" :model="form" label-width="220px" :rules="rules">
							<el-card class="box-card" id="baseContainer">
								<div slot="header" class="clearfix">
									<span>基础配置</span>
								</div>
								<el-form-item label="配置名称" prop="conf.confName"
									:rules="[{required: true, message: '请输入配置名称', trigger: 'blur'},{min:1,max:64,message:'配置名称长度不能超过64个字符'}]">
									<el-input v-model="form.conf.confName" placeholder="请输入配置名称" size="small"
										maxlength="64" clearable></el-input>
								</el-form-item>
								<el-form-item label="jdk路径" prop="confBase.jdkPath"
									:rules="[{required: true, message: '请输入jdk路径', trigger: 'blur'},{min:1,max:128,message:'jdk路径长度不能超过128个字符'}]">
									<el-input v-model="form.confBase.jdkPath" placeholder="请输入jdk路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="初始堆大小" prop="confBase.xms"
									:rules="[{required: true, message: '请输入初始堆大小', trigger: 'blur'}]">
									<el-input-number class="max-width-100-ratio" v-model="form.confBase.xms"
										controls-position="right" size="small" placeholder="请输入初始堆大小" :min="128"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="最大堆大小" prop="confBase.xmx"
									:rules="[{required: true, message: '请输入最大堆大小', trigger: 'blur'}]">
									<el-input-number class="max-width-100-ratio" v-model="form.confBase.xmx"
										controls-position="right" size="small" placeholder="请输入最大堆大小" :min="512"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="开启集群" prop="confBase.isEnableCluster">
									<el-switch v-model="form.confBase.isEnableCluster" active-color="#13ce66"
										:active-value="1" :inactive-value="0" inactive-color="#ff4949">
									</el-switch>
								</el-form-item>
							</el-card>
							<el-card class="box-card" id="yarnContainer">
								<div slot="header" class="clearfix">
									<span>Yarn配置</span>
								</div>
								<el-form-item label="yarn执行器路径" prop="confYarn.yarnPath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入yarn执行器路径', trigger: 'blur'},{min:1,max:128,message:'yarn执行器路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confYarn.yarnPath" placeholder="请输入yarn执行器路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="yarn程序主类" prop="confYarn.yarnMainClass"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入yarn程序主类', trigger: 'blur'},{min:1,max:128,message:'yarn程序主类长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confYarn.yarnMainClass" placeholder="请输入yarn程序主类" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="任务容器数量​" prop="confYarn.numContainers"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入任务容器数量​', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.numContainers"
										controls-position="right" size="small" placeholder="请输入任务容器数量" :min="1"
										:max="100" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="每个任务容器的内存(单位:M)" prop="confYarn.containerMemory"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入每个任务容器的内存', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.containerMemory"
										controls-position="right" size="small" placeholder="请输入每个任务容器的内存" :min="1024"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="每个任务容器的CPU核心数" prop="confYarn.containerVcores"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入每个任务容器的CPU核心数', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.containerVcores"
										controls-position="right" size="small" placeholder="请输入每个任务容器的CPU核心数" :min="1"
										:max="100" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="容器的内存(单位:M)" prop="confYarn.masterMemory"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入容器的内存', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.masterMemory"
										controls-position="right" size="small" placeholder="请输入容器的内存" :min="1024"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="AM容器的CPU核心数" prop="confYarn.masterVcores"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入AM容器的CPU核心数', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.masterVcores"
										controls-position="right" size="small" placeholder="请输入请输入AM容器的CPU核心数" :min="1"
										:max="100" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="优先级​" prop="confYarn.priority"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入优先级​', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confYarn.priority"
										controls-position="right" size="small" placeholder="请输入优先级" :min="1" :max="100"
										:controls="false"></el-input-number>
								</el-form-item>
							</el-card>
							<el-card class="box-card" id="dataxContainer">
								<div slot="header" class="clearfix">
									<span>Datax配置</span>
								</div>
								<el-form-item label="datax路径" prop="confDatax.dataxPath"
									:rules="[{required: true, message: '请输入datax路径', trigger: 'blur'},{min:1,max:128,message:'datax路径长度不能超过128个字符'}]">
									<el-input v-model="form.confDatax.dataxPath" placeholder="请输入datax路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="服务端地址" prop="confDatax.dataxService"
									:rules="[{required: false, message: '请输入服务端地址', trigger: 'blur'},{min:1,max:128,message:'服务端地址长度不能超过128个字符'}]">
									<el-input v-model="form.confDatax.dataxService" placeholder="请输入服务端地址" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="脚本路径" prop="confDatax.scriptPath"
									:rules="[{required: true, message: '请输入脚本路径', trigger: 'blur'},{min:1,max:128,message:'脚本路径长度不能超过128个字符'}]">
									<el-input v-model="form.confDatax.scriptPath" placeholder="请输入脚本路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="Jvm(单位:M)" prop="confDatax.dataxJvm"
									:rules="[{required: true, message: '请输入Jvm', trigger: 'blur'}]">
									<el-input-number class="max-width-100-ratio" v-model="form.confDatax.dataxJvm"
										controls-position="right" size="small" placeholder="请输入Jvm" :min="1024"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="AppName" prop="confDatax.dataxAppName"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入AppName', trigger: 'blur'},{min:1,max:32,message:'AppName长度不能超过32个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confDatax.dataxAppName" placeholder="请输入AppName" size="small"
										maxlength="32" clearable></el-input>
								</el-form-item>
								<el-form-item label="队列" prop="confDatax.dataxQueue"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入datax使用的队列', trigger: 'blur'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confDatax.dataxQueue" placeholder="请输入datax使用的队列" size="small"
										maxlength="32" clearable></el-input>
								</el-form-item>
							</el-card>
							<el-card class="box-card" id="sqlContainer">
								<div slot="header" class="clearfix">
									<span>Sql执行器配置</span>
								</div>
								<el-form-item label="执行器路径" prop="confSql.executePath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入执行器路径', trigger: 'blur'},{min:1,max:128,message:'执行器路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.executePath" placeholder="请输入执行器路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="脚本路径" prop="confSql.scriptPath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入脚本路径', trigger: 'blur'},{min:1,max:128,message:'脚本路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.scriptPath" placeholder="请输入脚本路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="执行器初始堆大小" prop="confSql.executeXms"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入执行器初始堆大小', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confSql.executeXms"
										controls-position="right" size="small" placeholder="请输入执行器初始堆大小" :min="128"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="执行器最大堆大小" prop="confSql.executeXmx"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入执行器最大堆大小', trigger: 'blur'}]">
									<el-input-number :disabled="form.confBase.isEnableCluster==1?false:true"
										class="max-width-100-ratio" v-model="form.confSql.executeXmx"
										controls-position="right" size="small" placeholder="请输入执行器最大堆大小" :min="512"
										:max="10240" :controls="false"></el-input-number>
								</el-form-item>
								<el-form-item label="logback路径" prop="confSql.logbackPath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入logback路径', trigger: 'blur'},{min:1,max:128,message:'logback路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.logbackPath" placeholder="请输入logback路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="依懒包路径" prop="confSql.libPath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入依懒包路径', trigger: 'blur'},{min:1,max:128,message:'依懒包路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.libPath" placeholder="请输入依懒包路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="程序主类" prop="confSql.mainClass"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入程序主类', trigger: 'blur'},{min:1,max:128,message:'程序主类长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.mainClass" placeholder="请输入程序主类" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="日志路径" prop="confSql.logPath"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入日志路径', trigger: 'blur'},{min:1,max:128,message:'日志路径长度不能超过128个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.logPath" placeholder="请输入日志路径" size="small"
										maxlength="128" clearable></el-input>
								</el-form-item>
								<el-form-item label="AppName" prop="confSql.executeAppName"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入执行器AppName', trigger: 'blur'},{min:1,max:32,message:'执行器AppName长度不能超过32个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.executeAppName" placeholder="请输入执行器AppName" size="small"
										maxlength="32" clearable></el-input>
								</el-form-item>
								<el-form-item label="执行器使用的队列" prop="confSql.executeQueue"
									:rules="[{required: form.confBase.isEnableCluster==1?true:false, message: '请输入执行器使用的队列', trigger: 'blur'},{min:1,max:32,message:'执行器使用的队列长度不能超过32个字符'}]">
									<el-input :disabled="form.confBase.isEnableCluster==1?false:true"
										v-model="form.confSql.executeQueue" placeholder="请输入执行器使用的队列" size="small"
										maxlength="32" clearable></el-input>
								</el-form-item>
							</el-card>
						</el-form>
					</el-scrollbar>
				</td>
			</tr>
		</table>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'confForm',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				defaultActive: 1,
				activeIndex: 1,
				rules: {},
				loading: false,
				dialogVisible: false,
				form: {
					conf: {
						//主键
						confId: undefined,
						//配置名称
						confName: undefined,
						//默认配置
						isDefault: 0,
					},
					confBase: {
						//主键
						id: undefined,
						//系统配置主表ID
						confId: undefined,
						//jdk路径
						jdkPath: undefined,
						//初始堆大小
						xms: 512,
						//最大堆大小
						xmx: 1024,
						//开启集群
						isEnableCluster: 0,
					},
					confYarn: {
						//主键
						id: undefined,
						//系统配置主表ID
						confId: undefined,
						//yarn执行器路径
						yarnPath: undefined,
						//yarn程序主类
						yarnMainClass: undefined,
						//任务容器数量
						numContainers: 1,
						//每个任务容器的内存
						containerMemory: 1025,
						//每个任务容器的CPU核心数
						containerVcores: 1,
						//AM容器的内存
						masterMemory: 1024,
						//AM容器的CPU核心数
						masterVcores: 1,
						//优先级​
						priority: 10
					},
					confDatax: {
						//主键
						id: undefined,
						//系统配置主表ID
						confId: undefined,
						//datax路径
						dataxPath: undefined,
						//脚本路径
						scriptPath: undefined,
						//datax_jvm
						dataxJvm: 1024,
						//datax_appname
						dataxAppName: 'EasyETLSync',
						//datax使用的队列
						dataxQueue: 'default',
						dataxService: undefined
					},
					confSql: {
						//主键
						id: undefined,
						//系统配置主表ID
						confId: undefined,
						//执行器路径
						executePath: undefined,
						//脚本路径
						scriptPath: undefined,
						//执行器初始堆大小
						executeXms: 512,
						//执行器最大堆大小
						executeXmx: 1024,
						//logback路径
						logbackPath: undefined,
						//依懒包路径
						libPath: undefined,
						//程序主类
						mainClass: undefined,
						//日志路径
						logPath: undefined,
						//执行器appname
						executeAppName: "EasyETLSqlExecute",
						//执行器使用的队列
						executeQueue: "default"
					}
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
			handleMenuSelect(index) {
				switch (parseInt(index)) {
					case 1:
						document.getElementById('baseContainer').scrollIntoView({
							behavior: 'smooth'
						});
						break;
					case 2:
						document.getElementById('yarnContainer').scrollIntoView({
							behavior: 'smooth'
						});
						break;
					case 3:
						document.getElementById('dataxContainer').scrollIntoView({
							behavior: 'smooth'
						});
						break;
					case 4:
						document.getElementById('sqlContainer').scrollIntoView({
							behavior: 'smooth'
						});
						break;
				}
			},
			openWin() {
				this.loading = false;
				if (this.form.conf.confId) {
					this.$ajax.get(this.getContentPath() + "/admin/etl/conf/getById", {
						"confId": this.form.conf.confId
					}).then(
						res => {
							this.loading = false;
							this.form.conf = {
								"confId": res.data.confId,
								"confName": res.data.confName
							};
							if (res.data.confYarn) {
								this.form.confYarn = res.data.confYarn;
							}
							if (res.data.confSql) {
								this.form.confSql = res.data.confSql;
							}
							if (res.data.confDatax) {
								this.form.confDatax = res.data.confDatax;
							}
							if (res.data.confBase) {
								this.form.confBase = res.data.confBase;
							}
						}).catch(err => {
						this.loading = false;
					});
				}
			},
			closeWin() {
				this.dialogVisible = false;
				this.clearValidate();
				this.form = this.$options.data().form;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			clearValidate() {
				this.$refs.form.clearValidate();
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						let form = {
							conf: JSON.stringify(this.form.conf),
							confDatax: JSON.stringify(this.form.confDatax),
							confSql: JSON.stringify(this.form.confSql),
							confYarn: JSON.stringify(this.form.confYarn),
							confBase: JSON.stringify(this.form.confBase)
						};
						this.$ajax.post(this.getContentPath() + "/admin/etl/conf/saveOrUpdate", form).then(
							res => {
								this.loading = false;
								const eventType = this.form.conf.confId == '' ? 1 : 2;
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
	.el-menu {
		height: 100%;
	}

	::v-deep .el-input-number {
		.el-input__inner {
			text-align: left;
		}
	}

	::v-deep .el-scrollbar__wrap {
		overflow-x: hidden;
	}
</style>