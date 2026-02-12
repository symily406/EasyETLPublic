<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body class="createTableDialog" >
		<div class="form-container"  :style="{height:dialogHeight}" v-loading="loading">
			 <codeEditor @change="codeChange" v-model="sqlCode" language="sql"></codeEditor>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit()">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	import codeEditor from "@/components/monaco/monaco.vue";
	export default {
		name: 'dataxSqlEdit',
		components: { codeEditor },
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				readColumns: [],
				taskId: undefined,
				dbSourceId:undefined,
				sqlCode:undefined,
				createSqlCode:undefined,
				catalog:undefined,
				scheam:undefined,
				sourceTableName:undefined,
				tableName:undefined,
				fullTableName:undefined,
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
				this.createTableSql();
			},
			closeWin() {
				this.dialogVisible = false;
				this.sqlCode =undefined;
			},
			createTableSql() {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/fieldTypeDict/createTableSql", {
					"taskId": this.taskId,
					"dbSourceId":this.dbSourceId,
					"scheam":this.scheam,
					"catalog":this.catalog,
					"sourceTableName":this.sourceTableName
				}).then(
					res => {
						this.loading = false;
						this.sqlCode=res.data.sql;
						this.tableName=res.data.tableName;
						this.fullTableName=res.data.fullTableName;
					}).catch(err => {
					this.loading = false;
				});
			},
			codeChange(code){
				this.createSqlCode=code;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit() {
				if(this.createSqlCode){
					this.loading = true;
					this.$ajax.post(this.getContentPath() + "/admin/etl/sqlActuator/createTable", {
						"dbSourceId":this.dbSourceId,
						"sql":this.createSqlCode
					}).then(
						res => {
							this.loading = false;
							let fullTableName="";
							if(res.data.catalog){
								fullTableName+=res.data.catalog+".";
							}
							if(res.data.schema){
								fullTableName+=res.data.schema+".";
							}
							fullTableName+=res.data.name;
							this.$emit("createTableCallBack",{"tableName":res.data.name,"fullTableName":fullTableName});
							this.closeWin();
						}).catch(err => {
						this.loading = false;
					});
				}
			}
		}
	}
</script>
<style scoped="scoped">
	.createTableDialog{
		.el-dialog__body{
			padding: 0px;
		}
	}
</style>