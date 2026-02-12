<template>
	<div class="step">
		<table border="0" width="100%" cellspacing="0" callpadding="5">
			<tr>
				<td width="100" align="center">选择目标</td>
				<td>&nbsp;</td>
				<td width="100" align="center" v-if="compare==false"><el-button type="text"
						v-if="taskStep==5||taskStep==50" @click="nextSetp(2)">修改</el-button></td>
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
										<td><el-select :disabled="isDisabled" v-model="form.tableName"
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
										<td width="100" v-if="compare==false"><el-button size="small" type="success"
												@click="createTable">一键生成目标表</el-button></td>
									</tr>
								</table>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType!=2" label="导入前准备语句" prop="preSql"
								:rules="[{required: false, message: '导入前准备语句', trigger: 'blur'}]">
								<el-input @change="handleChange" v-model="form.preSql" placeholder="导入前准备语句"
									size="small" maxlength="1024" :disabled="isDisabled" clearable></el-input>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType!=2" label="导入后准备语句" prop="postSql"
								:rules="[{required: false, message: '导入后准备语句', trigger: 'blur'}]">
								<el-input @change="handleChange" v-model="form.postSql" placeholder="导入后准备语句"
									size="small" maxlength="1024" clearable></el-input>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType==1" label="主键冲突" prop="writeMode"
								:rules="[{required: true, message: '主键冲突', trigger: 'blur'}]">
								<el-select @change="handleChange" v-model="form.writeMode" placeholder="主键冲突"
									size="small" :disabled="isDisabled" class="max-width-100-ratio">
									<el-option v-for="item in writeModes" :key="item.value" :label="item.name"
										:value="item.value" :disabled="item.disabled">
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType==2" label="写入模式" prop="writeMode"
								:rules="[{required: true, message: '写入模式', trigger: 'blur'}]">
								<el-radio-group @change="handleChange" v-model="form.hiveMode" size="small">
									<el-radio v-for="(item, index) in hiveModes" :key="index"
										:label="item.value">{{ item.name }}</el-radio>
								</el-radio-group>
							</el-form-item>
							<el-form-item v-if="form.dbSourceId&&sourceType==2" label="分区" prop="pt"
								:rules="[{required: true, message: '请填分区信息', trigger: 'blur'}]">
								<el-autocomplete @change="handleChange" v-model="form.pt" placeholder="请填分区信息"
									size="small" clearable :fetch-suggestions="querySearch"
									class="max-width-100-ratio"></el-autocomplete>
							</el-form-item>
						</el-form>
					</td>
				</tr>
			</table>
			<table border="0" width="100%" cellspacing="0" callpadding="5" v-if="taskStep==2">
				<tr>
					<td width="100">&nbsp;</td>
					<td align="center">
						<el-button size="small" @click="nextSetp(1)">上一步</el-button>
						<el-button type="primary" size="small" @click="onSubmit('form',3)">下一步</el-button>
					</td>
					<td width="100">&nbsp;</td>
				</tr>
			</table>
		</div>
		<sqlEdit ref="sqlEdit" dialogTitle="一键生成目标表" dialogWidth="1000px" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @createTableCallBack="createTableCallBack" />
	</div>
</template>
<script>
	import sqlEdit from './sqlEdit';
	import {
		dicts
	} from "@/mixins/mixins";
	import {
		setTaskData
	} from "@/utils/localStore.js";
	export default {
		name: 'dataxStep02',
		components: {
			sqlEdit
		},
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
			},
		},
		mixins: [dicts],
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
				writerColumns: [],
				tableSplitPks: [],
				isSchema: 0,
				sourceType: 0,
				writeModes: [],
				hiveModes: [],
				dataChange: 0,
				taskId: undefined,
				sourceTableName: undefined,
				form: {
					//主键
					writeId: undefined,
					//归属任务
					taskId: undefined,
					//归属项目
					projectId: undefined,
					projectWorkSpaceLayerRelId: undefined,
					projectWorkSpaceId: undefined,
					workSpaceType: undefined,
					taskFolderId: undefined,
					//是否是HIVE
					isHive: 0,
					//数据源
					dbSourceId: undefined,
					dbCatalog: undefined,
					//schema
					dbSchema: undefined,
					//表名
					tableName: undefined,
					preSql: undefined,
					postSql: undefined,
					writeMode: 'insert',
					hiveMode: 'truncate',
					pt: undefined,
					tablePath:undefined,
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
			destroy() {
				this.rules = {};
				this.loading = true;
				this.dbSources = [];
				this.dbSourceSchemas = [];
				this.dbSourcesTables = [];
				this.writerColumns = [];
				this.tableSplitPks = [];
				this.writeModes = [];
				this.hiveModes = [];
				this.isSchema = 0;
				this.sourceType = 0;
				this.dataChange = 0;
				this.taskId = undefined;
				//主键
				this.form.writeId = undefined;
				//归属任务
				this.form.taskId = undefined;
				//归属项目
				this.form.projectId = undefined;
				this.form.projectWorkSpaceLayerRelId = undefined;
				this.form.projectWorkSpaceId = undefined;
				this.form.workSpaceType = undefined;
				this.form.taskFolderId = undefined;
				//是否是HIVE
				this.form.isHive = 0;
				this.form.pt = undefined;
				//数据源
				this.form.dbSourceId = undefined;
				this.form.dbCatalog = undefined;
				this.form.dbSchema = undefined;
				//表名
				this.form.tableName = undefined;
				this.form.fullTableName = undefined;
				this.form.preSql = undefined;
				this.form.postSql = undefined;
				this.form.writeMode = 'insert';
				this.form.hiveMode = 'truncate';
				//数据过虑条件
				this.form.queryCondition = undefined;
				//切分键
				this.form.splitPk = undefined;
				//高级配置
				this.form.configuration = undefined;
				this.form.tablePath=undefined;
				if (this.$refs['form']) {
					this.$nextTick(() => {
						try {
							this.$refs['form'].clearValidate();
						} catch (err) {
							console.log("哈哈,第二步这里有个异常!!!!!!");
						}
					});
				}
			},
			initMode() {
				switch (this.form.isHive) {
					case 0:
						this.initWriteModes();
						break;
					case 1:
						this.initHiveModes();
						break;
				}
			},
			initWriteModes() {
				let that = this;
				this.initDict("WRITE_MODE", function(res) {
					that.writeModes = res.data;
				}, function(error) {

				});
			},
			initHiveModes() {
				let that = this;
				this.initDict("DATAX_MODE", function(res) {
					that.hiveModes = res.data;
				}, function(error) {

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
								this.sourceTableName = res.data.tableName;
								
							}
						}).catch(err => {});
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
						this.initTaskWrite();
					}).catch(err => {

				});
			},
			initTaskWrite() {
				let taskId = this.taskId;
				if (taskId) {
					let url = "/admin/etl/batchTask/batchProjectLayerTaskWrite/getByTaskId";
					if (this.backup) {
						url = "/admin/etl/projectTaskBackup/queryBatchProjectLayerTaskWriteBakeByTaskId";
					}
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
						}).catch(err => {
						this.loading = false;
					});
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
				this.form.dbCatalog=undefined;
				this.writerColumns = [];
				this.tableSplitPks = [];
				this.dbSourcesTables = [];
				this.form.tableName = undefined;
				this.form.preSql = undefined;
				this.form.splitPk = undefined;
				this.form.postSql = undefined;
				this.form.writeMode = 'insert';
				this.form.hiveMode = 'truncate';
				//数据过虑条件
				this.form.queryCondition = undefined;
				this.form.configuration = undefined;
				this.form.tablePath=undefined;
				this.$emit("writeColumnsCallBack", []);
				this.initSchema(val);
				this.dataChange = 1;
			},
			initSchema(val) {
				this.form.isHive = 0;
				this.sourceType = 0;
				let db = this.dbSources.filter(item => item.dbSourceId === val)[0];
				if (db) {
					console.log(db);
					this.isSchema = db.isSchema;
					this.sourceType = db.sourceType;
					if (db.sourceType == 2) {
						this.form.isHive = 1;
					}
					this.form.dbCatalog = db.dbSchema;
					this.form.dbSchema = db.dbCatalog;
					this.queryDbSourcesTables();
					this.initMode();
				}
			},
			dbSchemaHandleChange(val) {
				this.dbSchemaHandleClear();
				this.queryDbSourcesTables();
				this.dataChange = 1;
			},
			dbSchemaHandleClear() {
				this.writerColumns = [];
				this.tableSplitPks = [];
				this.dbSourcesTables = [];
				this.form.tableName = undefined;
				this.form.preSql = undefined;
				this.form.postSql = undefined;
				this.form.splitPk = undefined;
				this.form.writeMode = 'insert';
				this.form.hiveMode = 'overwrite';
				//数据过虑条件
				this.form.queryCondition = undefined;
				this.form.configuration = undefined;
				this.form.tablePath=undefined;
				this.$emit("writeColumnsCallBack", []);
			},
			queryDbSourcesTables() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/queryDbSourcesTables", {
					"dbSourceId": this.form.dbSourceId
				}).then(
					res => {
						this.dbSourcesTables = res.data;
						if (this.form.writeId) {
							this.queryDbSourcesColumns();
						}
						this.hiveTablePath();
					}).catch(err => {

				});
			},
			hiveTablePath(){
				if(!this.form.tableName){
					return;
				}
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/hiveTablePath", {
					"dbSourceId": this.form.dbSourceId,
					"tableName":this.form.tableName
				}).then(
					res => {
						this.form.tablePath=res.data;
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
				this.dbTableHandleClear();
				this.queryDbSourcesColumns();
				this.createTruncate();
				this.dataChange = 1;
				if (clickType == 1) {
					this.$message({
						message: '字段刷新成功',
						type: 'success'
					});
				}
			},
			createTruncate() {
				this.form.preSql=undefined;
				if(this.sourceType!=2){
					let sql = "truncate table ";
					if(this.form.dbCatalog){
						sql +=this.form.dbCatalog+".";
					}
					if(this.form.dbSchema){
						sql +=this.form.dbSchema+".";
					}
					sql += this.form.tableName + ";";
					this.form.preSql = sql;
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
						this.writerColumns = res.data;
						this.$emit("writeColumnsCallBack", res.data);
						this.initTableSplitPks();
						this.hiveTablePath();
					}).catch(err => {});
			},
			dbTableHandleClear() {
				this.writerColumns = [];
				this.tableSplitPks = [];
				this.form.splitPk = undefined;
				this.$emit("writeColumnsCallBack", []);
			},
			initTableSplitPks() {
				let tableSplitPks = [];
				this.writerColumns.forEach(item => {
					if (item.isPrimaryKey == 1) {
						tableSplitPks.push(item);
					}
				});
				this.tableSplitPks = tableSplitPks;
			},
			nextSetp(nextSetp) {
				this.$emit('nextSetp', nextSetp);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			createTable() {
				this.$refs['sqlEdit'].taskId = this.taskId;
				this.$refs['sqlEdit'].dbSourceId = this.form.dbSourceId;
				this.$refs['sqlEdit'].schema = this.form.dbSchema;
				this.$refs['sqlEdit'].catalog = this.form.dbCatalog;
				this.$refs['sqlEdit'].sourceTableName = this.sourceTableName;
				this.$refs['sqlEdit'].dialogVisible = true;
			},
			createTableCallBack(table) {
				this.form.tableName = table.tableName;
				this.form.fullTableName = table.fullTableName;
				if (this.sourceType == 1) {
					this.createTruncate();
				}
				this.queryDbSourcesTables();
				this.dataChange = 1;
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
						this.form.taskStep = 3;
						this.loading = true;
						this.$ajax.post(this.getContentPath() +
								"/admin/etl/batchTask/batchProjectLayerTaskWrite/saveOrUpdate", this.form)
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
				setTaskData("step02_" + this.taskId, JSON.stringify(this.form));
				this.$emit("parameterCallBack");
			}
		}
	}
</script>
<style scoped="scoped">

</style>