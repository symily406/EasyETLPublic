<template>
	<div class="step">
		<table border="0" width="100%" cellspacing="0" callpadding="5">
			<tr>
				<td width="100" align="center">选择来源</td>
				<td>&nbsp;</td>
				<td width="100" align="center" v-if="compare==false"><el-button type="text"
						v-if="taskStep==5||taskStep==50" @click="nextSetp(1)">修改</el-button></td>
			</tr>
		</table>
		<el-divider></el-divider>
		<div v-loading="loading">
			<table border="0" width="100%" cellspacing="0" callpadding="0">
				<tr>
					<td class="column-container">
						<div class="task-mask" v-if="taskStep==5||taskStep==50"></div>
						<el-form class="max-width-100-ratio" ref="form" :model="form" label-width="120px"
							:rules="rules">
							<el-form-item label="数据源" prop="dbSourceId"
								:rules="[{required: true, message: '请选择数据源', trigger: 'blur'}]">

								<table border="0" width="100%" cellspacing="0" callpadding="0">
									<tr>
										<td><el-select :disabled="isDisabled" v-model="form.dbSourceId"
												placeholder="请选择数据源" size="small" filterable class="max-width-100-ratio"
												@change="dbSourceHandleChange">
												<el-option v-for="item in dbSources" :key="item.dbSourceId"
													:label="item.label" :value="item.dbSourceId">
												</el-option>
											</el-select>
										</td>
										<td width="50" v-if="compare==false"><el-button size="small" type="warning"
												@click="validateConnect()" icon="el-icon-connection"
												title="重连"></el-button></td>
									</tr>
								</table>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId" label="表名" prop="tableName"
								:rules="[{required: true, message: '请选择数表名', trigger: 'blur'}]">
								<table border="0" width="100%" cellspacing="0" callpadding="0">
									<tr>
										<td>
											<el-select :disabled="isDisabled" v-model="form.tableName"
												placeholder="请选择表名" size="small" filterable class="max-width-100-ratio"
												clearable @change="dbTableHandleChange" @clear="dbTableHandleClear">
												<el-option v-for="item in dbSourcesTables" :key="item.name"
													:label="item.name" :value="item.name">
												</el-option>
											</el-select>
										</td>
										<td width="50" v-if="compare==false"><el-button size="small" type="primary"
												@click="loadColumn(1)" icon="el-icon-refresh"
												title="重新加载表结构"></el-button></td>
									</tr>
								</table>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType==2" label="分区" prop="pt"
								:rules="[{required: false, message: '请填分区信息', trigger: 'blur'}]">
								<el-autocomplete @change="handleChange" v-model="form.pt" placeholder="默认取表下所有分区"
									size="small" clearable :fetch-suggestions="querySearch"
									class="max-width-100-ratio"></el-autocomplete>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType!=2" label="数据过虑" prop="queryCondition"
								:rules="[{required: false, message: '数据过虑', trigger: 'blur'}]">
								<el-input @change="handleChange" :disabled="isDisabled" type="textarea"
									v-model="form.queryCondition" placeholder="请参考相关SQL语法填写过虑语句(不要填写where关键字)"
									size="small" maxlength="64" clearable></el-input>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType!=2" label="切分键" prop="splitPk"
								:rules="[{required: false, message: '请选择切分键', trigger: 'blur'}]">
								<el-select :disabled="isDisabled" v-model="form.splitPk" placeholder="请选择切分键"
									@change="handleChange" size="small" filterable class="max-width-100-ratio"
									clearable>
									<el-option v-for="item in tableSplitPks" :key="item.columnName"
										:label="item.columnName" :value="item.columnName">
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item label="fetchSize" prop="fetchSize"
								:rules="[{required: false, message: '高级配置', trigger: 'blur'}]" v-show="false">
								<el-input v-model="form.fetchSize" :disabled="isDisabled" @change="handleChange"
									placeholder="每次从数据库获取的行数" size="small" @input="handleInput"
									@blur="handleBlur"></el-input>
							</el-form-item>
						</el-form>
					</td>
				</tr>
			</table>
			<table border="0" width="100%" cellspacing="0" callpadding="5" v-if="taskStep==1">
				<tr>
					<td width="100">&nbsp;</td>
					<td align="center"><el-button type="primary" size="small"
							@click="onSubmit('form',2)">下一步</el-button></td>
					<td width="100">&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
</template>
<script>
	import {
		stringify
	} from 'qs';
	import {
		setTaskData
	} from "@/utils/localStore.js";
	export default {
		name: 'dataxStep01',
		components: {},
		props: {
			isDisabled: {
				type: Boolean,
				default: false
			},
			taskStep: {
				type: Number,
				default: 1
			},
			compare: {
				type: Boolean,
				default: false
			},
			backup: {
				type: Boolean,
				default: false
			}
		},
		mixins: [],
		data() {
			return {
				restaurants: [{
					"value": "pt="
				}],
				rules: {},
				loading: true,
				dbSources: [],
				dbSourceSchemas: [],
				dbSourcesTables: [],
				readColumns: [],
				tableSplitPks: [],
				isSchema: 0,
				sourceType: 0,
				dataChange: 0,
				taskId: undefined,
				dbIsConnect: false,
				form: {
					//主键
					readId: undefined,
					//归属任务
					taskId: undefined,
					//归属项目
					projectId: undefined,
					//归属批处理分层
					projectWorkSpaceLayerRelId: undefined,
					projectWorkSpaceId: undefined,
					workSpaceType: undefined,
					taskFolderId: undefined,
					//是否是HIVE
					isHive: 0,
					//数据源
					dbSourceId: undefined,
					dbCatalog: undefined,
					dbSchema: undefined,
					//表名
					tableName: undefined,
					//数据过虑条件
					queryCondition: undefined,
					//切分键
					splitPk: undefined,
					//高级配置
					fetchSize: undefined,
					tablePath: undefined,
					pt: undefined
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
			this.destroy();
		},
		//销毁完成状态
		destroyed() {

		},
		methods: {
			querySearch(queryString, cb) {
				var restaurants = this.restaurants;
				var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
				// 调用 callback 返回建议列表的数据
				cb(results);
			},
			createFilter(queryString) {
				return (restaurant) => {
					return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
				};
			},
			handleInput(value) {
				this.form.fetchSize = value.replace(/[^\d]/g, '');
			},
			// 失去焦点时进一步校验，可提示用户
			handleBlur() {
				if (!this.form.fetchSize) {
					return;
				}
				if (!/^\d+$/.test(this.form.fetchSize)) {
					this.$message.warning('只能输入数字');
					this.form.fetchSize = undefined; // 清空非法输入
					return;
				}
				const num = parseInt(this.form.fetchSize, 10);
				if (num <= 0) {
					this.$message.warning('请输入大于 0 的正整数');
					this.form.fetchSize = undefined; // 清空非法输入
				}
			},
			destroy() {
				this.rules = {};
				this.loading = true;
				this.dbSources = [];
				this.dbSourceSchemas = [];
				this.dbSourcesTables = [];
				this.readColumns = [];
				this.tableSplitPks = [];
				this.isSchema = 0;
				this.sourceType = 0;
				this.dataChange = 0;
				this.taskId = undefined;
				//主键
				this.form.readId = undefined;
				//归属任务
				this.form.taskId = undefined;
				//归属项目
				this.form.projectId = undefined;
				//归属批处理分层
				this.form.projectWorkSpaceLayerRelId = undefined;
				this.form.projectWorkSpaceId = undefined;
				this.form.workSpaceType = undefined;
				this.form.taskFolderId = undefined;
				//是否是HIVE
				this.form.isHive = 0;
				//数据源
				this.form.dbSourceId = undefined;
				this.form.dbCatalog = undefined;
				this.form.dbSchema = undefined;
				//表名
				this.form.tableName = undefined;
				//数据过虑条件
				this.form.queryCondition = undefined;
				//切分键
				this.form.splitPk = undefined;
				//高级配置
				this.form.fetchSize = undefined;
				this.form.tablePath = undefined;
				if (this.$refs['form']) {
					this.$nextTick(() => {
						try {
							this.$refs['form'].clearValidate();
						} catch (err) {
							console.log("哈哈,第一步这里有个异常!!!!!!");
						}
					});
				}
			},
			initDbSource() {
				if (!this.taskId) {
					return;
				}
				let projectId = this.projectId;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/queryProjectDbSource", {
					"projectId": projectId
				}).then(
					res => {
						this.dbSources = res.data;
						this.initTaskRead();
					}).catch(err => {

				});
			},
			initTaskRead() {
				let url = "/admin/etl/batchTask/batchProjectLayerTaskRead/getByTaskId";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/getBatchTaskReadByTaskId";
				}
				let taskId = this.taskId;
				if (taskId) {
					this.loading = true;
					this.$ajax.get(this.getContentPath() + url, {
						"taskId": taskId
					}).then(
						res => {
							this.loading = false;
							if (res.data) {
								this.form = res.data;
								this.initSchema(this.form.dbSourceId);
								this.validateConnect();
							}
						}).catch(err => {});
				}
			},
			validateConnect() {
				if (this.form.dbSourceId && this.backup == false) {
					this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/validateConnect", {
						"dbSourceId": this.form.dbSourceId
					}).then(
						res => {
							if (!res.data.connect) {
								this.$message.error(res.data.message);
							}
						}).catch(err => {

					});
				}
			},
			dbSourceHandleChange(val) {
				this.dbSourceSchemas = [];
				this.form.dbSchema = undefined;
				this.readColumns = [];
				this.tableSplitPks = [];
				this.dbSourcesTables = [];
				this.form.tableName = undefined;
				this.form.splitPk = undefined;
				this.$emit("readColumnsCallBack", []);
				this.dataChange = 1;
				this.initSchema(val);
			},
			initSchema(val) {
				this.form.isHive = 0;
				let db = this.dbSources.filter(item => item.dbSourceId === val)[0];
				if (db) {
					this.isSchema = db.isSchema;
					this.sourceType = db.sourceType;
					if (db.sourceType == 2) {
						this.form.isHive = 1;
					}
					this.form.dbCatalog = db.catalog;
					this.form.dbSchema = db.schema;
					this.queryDbSourcesTables();
				}
			},
			dbSchemaHandleChange(val) {
				this.dbSchemaHandleClear();
				this.queryDbSourcesTables();
			},
			dbSchemaHandleClear() {
				this.readColumns = [];
				this.tableSplitPks = [];
				this.dbSourcesTables = [];
				this.form.tableName = undefined;
				this.form.splitPk = undefined;
				this.form.dbCatalog = undefined;
				this.form.dbSchema = undefined;
				this.dataChange = 1;
				this.$emit("readColumnsCallBack", []);
			},
			queryDbSourcesTables() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/queryDbSourcesTables", {
					"dbSourceId": this.form.dbSourceId
				}).then(
					res => {
						this.dbSourcesTables = res.data;
						if (this.form.readId) {
							this.queryDbSourcesColumns();
						}
					}).catch(err => {});
			},
			hiveTablePath() {
				if (!this.form.tableName) {
					return;
				}
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/hiveTablePath", {
					"dbSourceId": this.form.dbSourceId,
					"tableName": this.form.tableName
				}).then(
					res => {
						this.form.tablePath = res.data;
					}).catch(err => {

				});
			},
			dbTableHandleChange(val) {
				this.dataChange = 1;
				this.loadColumn(0);
			},
			loadColumn(clickType) {
				if (clickType == 1 && !this.form.tableName) {
					this.$message.error('请选择表名');
					return;
				}
				this.dataChange = 1;
				this.dbTableHandleClear();
				this.queryDbSourcesColumns();
				if (clickType == 1) {
					this.$message({
						message: '字段刷新成功',
						type: 'success'
					});
				}
			},
			queryDbSourcesColumns() {
				if (!this.form.tableName) {
					return;
				}
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/queryDbSourcesColumns", {
					"dbSourceId": this.form.dbSourceId,
					"tableName": this.form.tableName
				}).then(
					res => {
						this.readColumns = res.data;
						this.$emit("readColumnsCallBack", res.data);
						this.initTableSplitPks();
						this.hiveTablePath();
					}).catch(err => {});
			},
			dbTableHandleClear() {
				this.readColumns = [];
				this.tableSplitPks = [];
				this.form.splitPk = undefined;
				this.$emit("readColumnsCallBack", []);
			},
			initTableSplitPks() {
				let tableSplitPks = [];
				this.readColumns.forEach(item => {
					if (item.isPrimaryKey == 1) {
						tableSplitPks.push(item);
					}
				});
				this.tableSplitPks = tableSplitPks;
			},
			nextSetp(nextSetp) {
				this.dataChange = 0;
				this.$emit("sourceTable", this.form.tableName);
				this.$emit('nextSetp', nextSetp);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			handleChange(val) {
				this.dataChange = 1;
				this.setFormContent();
			},
			onSubmit(formName, nextSetp) {
				this.setFormContent();
				if (this.dataChange == 0) {
					this.nextSetp(nextSetp);
					return;
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form.taskId = this.taskId;
						this.form.taskStep = 2;
						this.loading = true;
						this.$ajax.post(this.getContentPath() +
								"/admin/etl/batchTask/batchProjectLayerTaskRead/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								this.nextSetp(nextSetp);
							}).catch(err => {
								this.loading = false;
							});

					} else {
						return false;
					}
				});
			},
			setFormContent() {
				setTaskData("step01_" + this.taskId, JSON.stringify(this.form));
				this.$emit("parameterCallBack");
			}
		}
	}
</script>
<style scoped="scoped">
</style>