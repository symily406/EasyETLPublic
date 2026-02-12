<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading"  @sort-change="tableSortChange">
			<el-table-column prop="taskName" label="任务名称" sortable>
				<template slot-scope="scope">
					{{scope.row.taskName}}
				</template>
			</el-table-column>
			<el-table-column prop="projectName" label="所属项目" sortable>
				<template slot-scope="scope">
					{{scope.row.projectName}}
				</template>
			</el-table-column>
			<el-table-column prop="cornStatusDesc" label="调度状态" width="120" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.cornStatusDesc}}
				</template>
			</el-table-column>
			<el-table-column prop="workSpaceTypeDesc" label="任务类型" width="120" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.workSpaceTypeDesc}}
				</template>
			</el-table-column>
			<el-table-column prop="corn" label="计划执行时间" width="180" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.corn}}
				</template>
			</el-table-column>
			<el-table-column prop="startTime" label="开始时间" width="150" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.startTime}}
				</template>
			</el-table-column>
			<el-table-column prop="endTime" label="结束时间" width="150" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.endTime}}
				</template>
			</el-table-column>
			<el-table-column prop="statusDesc" label="任务状态" width="120" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.statusDesc}}
				</template>
			</el-table-column>
			<el-table-column prop="usageTime" label="用时(毫秒)" width="120" align="center" sortable>
				<template slot-scope="scope">
					{{scope.row.usageTime}}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="taskEdit(scope.row)" type="text" size="small">
						查看任务
					</el-button>
					<el-button @click.native.prevent="showRunTask(scope.row)" type="text" size="small">
						任务详情
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eRunTaskJob ref="eRunTaskJobRef" dialogTitle="执行列表" dialogWidth="100vw" dialogHeight="100vh"
			:dialogTop="this.$store.state.global.dialogTop"/>
	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eRunTaskJob from '../runTaskJob/index';
	export default {
		name: 'taskJobIndex',
		components: {
			ePage,
			eHeader,
			eRunTaskJob
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//任务名称
					taskName: undefined,
					//归属项目
					projectId: undefined,
					cornStatus: undefined,
					//任务运行开始时间
					startDate: undefined,
					//任务运行结束时间
					endDate: undefined,
					planeDate:undefined,
					status: undefined,
					orderType:undefined,
					orderColumn:undefined
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			window.addEventListener('resize', this.initTableHeight);
			this.queryParams.planeDate=this.$moment().format('YYYY-MM-DD');
			this.initData(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/taskJob/queryPage", this.addPageParam(queryParams, this
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
				this.queryParams.planeDate=this.$moment().format('YYYY-MM-DD');
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			tableSortChange(selection){
				let column=selection.prop;
				let order=selection.order;
				this.queryParams.orderType=order;
				this.queryParams.orderColumn=column;
				this.initData(this.queryParams);
			},
			taskEdit(row) {
				let task = {
					"taskId": row.taskId,
					"taskName": row.taskName,
					"projectId": row.projectId,
					"projectWorkSpaceLayerRelId": row.projectWorkSpaceLayerRelId,
					"projectWorkSpaceId": row.projectWorkSpaceId,
					"workSpaceType": row.workSpaceType
				};
				this.addTabs(task);
				this.routeView(task);
			},
			addTabs(task) {
				if (task) {
					let tabs = this.getTaskTabs(task.projectId);
					let tab = tabs.filter(item => item.taskId === task.taskId)[0];
					if (!tab) {
						tab = {
							"taskId": task.taskId,
							"taskName": task.taskName,
							"projectId": task.projectId,
							"projectWorkSpaceLayerRelId": task.projectWorkSpaceLayerRelId,
							"projectWorkSpaceId": task.projectWorkSpaceId,
							"workSpaceType": task.workSpaceType,
							"isClose": true
						};
						tabs.push(tab);
					}
					this.setTaskActiveTab(tab.taskId,task.projectId);
					this.setTaskTabs(tabs,task.projectId);
				}
			},
			routeView(task) {
				const {
					href
				} = this.$router.resolve({
					path: '/workSpace/index',
					query: {
						projectId: task.projectId,
						projectWorkSpaceId: task.projectWorkSpaceId,
						workSpaceType: task.workSpaceType
					}
				});
				window.open(href, '_blank');
			},
			getSessionTabKey(keyName,projectId) {
				let key=keyName.concat("_").concat(projectId);
				return key;
			},
			setTaskTabs(tabs,projectId) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTabName,projectId), JSON.stringify(tabs));
			},
			getTaskTabs(projectId) {
				let tabs = [];
				let storeTabs = localStorage.getItem(this.getSessionTabKey(this.$store.state.global.taskTabName,projectId));
				if (storeTabs) {
					tabs = JSON.parse(storeTabs);
				}
				return tabs;
			},
			setTaskActiveTab(val,projectId) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTableIndexName,projectId), val);
			},
			showRunTask(row){
				this.$refs['eRunTaskJobRef'].workSpaceType=parseInt(row.workSpaceType);
				this.$refs['eRunTaskJobRef'].taskId=row.taskId;
				this.$refs['eRunTaskJobRef'].dialogVisible=true;
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '待运行任务添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '待运行任务修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '待运行任务删除成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
					.offsetHeight - 50;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>