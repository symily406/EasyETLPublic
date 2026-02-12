<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="headr-container">
			<el-form :inline="true" :model="queryParams">
				<el-form-item>
					<el-select v-model="queryParams.projectId" placeholder="项目名称" clearable size="small">
						<el-option v-for="item in projects" :key="item.projectId" :label="item.projectName"
							:value="item.projectId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="">
					<el-input v-model="queryParams.taskName" placeholder="任务名称" size="small"
						clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
					<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				</el-form-item>
			</el-form>
		</div>
		<el-table size="mini" border :data="tableData" :height="dialogHeight"
			:style="{width:dialogWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading"  @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center" fixed="left">
			</el-table-column>
			<el-table-column prop="taskName" label="任务名称">
				<template slot-scope="scope">
					{{scope.row.taskName}}[{{scope.row.workSpaceTypeDesc}}]
				</template>
			</el-table-column>
			<el-table-column prop="projectName" label="所属项目" width="200">
				<template slot-scope="scope">
					{{scope.row.projectName}}
				</template>
			</el-table-column>
			<el-table-column prop="addTime" label="添加时间" width="200" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
		</el-table>
		<ePage  :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="taskCallBack()">确 定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	import {
		dialogMixin,indexMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	export default {
		name: 'projectTask',
		components: {ePage},
		props: {
			projectTaskInfo: {
				type: Object,
				default: {}
			}
		},
		mixins: [dialogMixin,indexMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				projects: [],
				tableData:[],
				selectData:[],
				queryParams: {
					"taskName": '',
					"projectId": '',
					"projectIds":'',
					"currentTaskId":''
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
				this.initProject();
			},
			initProject() {
				this.$ajax.get(this.getContentPath() +
					"/admin/etl/project/queryAll", {}).then(
					res => {
						this.projects = res.data;
						this.initData(this.queryParams);
					}).catch(err => {

				});
			},
			initData(queryParams) {
				let projectIds=[];
				this.projects.forEach(item=>{
					projectIds.push(item.projectId);
				});
				this.queryParams.projectIds="";
				if(!this.queryParams.projectId){
					this.queryParams.projectIds=projectIds.join(",");
				}
				this.queryParams.currentTaskId=this.projectTaskInfo.taskId;
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/queryPage", this.addPageParam(
					queryParams, this
					.page)).then(res => {
					this.loading = false;
					this.page = this.$app.extend(this.page, this.$app.setPage(res.data));
					this.tableData = res.data.records;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			handleSelectionChange(data) {
				this.selectData = data;
				
			},
			closeWin() {
				this.queryParams = this.$options.data().queryParams;
				this.dialogVisible = false;
				this.tableData=[];
			},
			taskCallBack(){
				this.$emit("eventCallBack",this.selectData);
				this.closeWin();
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>