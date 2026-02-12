<template>
	<el-dialog class="dialogClass" :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false"
		:width="dialogWidth" @opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="app-container" ref="appContainer">
			<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage"
				@refreshPage="refreshPage" />
			<el-table size="mini" border :data="tableData" :height="tableHeight" style="width: 100%;"
				@sort-change="tableSortChange" :header-cell-style="{textAlign:'center'}" v-loading="loading"
				@selection-change="handleSelectionChange">
				<el-table-column prop="JobId" label="执行ID" sortable>
					<template slot-scope="scope">
						{{scope.row.jobId}}
					</template>
				</el-table-column>
				<el-table-column prop="ip" label="执行节点IP" sortable>
					<template slot-scope="scope">
						{{scope.row.ip}}
					</template>
				</el-table-column>
				<el-table-column prop="addTime" label="添加时间" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.addTime}}
					</template>
				</el-table-column>
				<el-table-column prop="startTime" label="运行开始时间" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.startTime}}
					</template>
				</el-table-column>
				<el-table-column prop="endTime" label="运行结束时间" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.endTime}}
					</template>
				</el-table-column>
				<el-table-column prop="usageTime" label="用时(毫秒)" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.usageTime}}
					</template>
				</el-table-column>
				<el-table-column v-if="workSpaceType==1" prop="readRecords" label="读取记录数(条)" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.readRecords}}
					</template>
				</el-table-column>
				<el-table-column v-if="workSpaceType==1" prop="readWriteFailures" label="读写错误数(条)" align="center"
					sortable>
					<template slot-scope="scope">
						{{scope.row.readWriteFailures}}
					</template>
				</el-table-column>
				<el-table-column prop="statusName" label="任务状态" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.statusName}}
					</template>
				</el-table-column>
				<el-table-column prop="addTypeName" label="添加方式" align="center" sortable>
					<template slot-scope="scope">
						{{scope.row.addTypeName}}
					</template>
				</el-table-column>
				<el-table-column label="操作" width="100" align="center">
					<template slot-scope="scope">
						<el-button @click.native.prevent="logInfo(scope.row)" type="text" size="small">
							运行日志
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		</div>
		<eLogInfo ref="eLogInfoRef" dialogTitle="任务日志" dialogWidth="50vw" />
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		indexMixin,
		dialogMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eLogInfo from './module/logInfo';
	export default {
		name: 'runTaskJobIndex',
		components: {
			ePage,
			eHeader,
			eLogInfo
		},
		props: {

		},
		mixins: [indexMixin, dialogMixin],
		data() {
			return {
				taskId: undefined,
				workSpaceType: 0,
				queryParams: {
					taskId: undefined,
					orderType: undefined,
					orderColumn: undefined,
					//任务添加方式
					addType: undefined,
					//任务状态
					status: undefined,
					//任务运行开始时间
					queryStartTime: undefined,
					//任务运行结束时间
					queryEndTime: undefined,
					//用时
					usageTime: undefined
				}
			}
		},
		mounted() {

		},
		created() {
			window.addEventListener('resize', this.initTableHeight);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			openWin() {
				this.$nextTick(() => {
					this.initTableHeight();
				});
				this.queryParams.taskId = this.taskId;
				this.queryParams.queryStartTime = this.$moment().format('YYYY-MM-DD') + " 00:00:00";
				this.queryParams.queryEndTime = this.$moment().format('YYYY-MM-DD') + " 23:59:59";
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.queryParams = this.$options.data().queryParams;
			},
			tableSortChange(selection) {
				let column = selection.prop;
				let order = selection.order;
				this.queryParams.orderType = order;
				this.queryParams.orderColumn = column;
				this.initData(this.queryParams);
			},
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJob/queryPage", this.addPageParam(
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
				this.queryParams.taskId = this.taskId;
				this.queryParams.queryStartTime = this.$moment().format('YYYY-MM-DD') + " 00:00:00";
				this.queryParams.queryEndTime = this.$moment().format('YYYY-MM-DD') + " 23:59:59";
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			logInfo(row) {
				this.$refs['eLogInfoRef'].jobId = row.jobId;
				this.$refs['eLogInfoRef'].dialogVisible = true;
			},
			initTableHeight() {
				this.tableHeight = window.innerHeight - this.$refs['headerContainer'].$el.offsetHeight - 250;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.dialogClass {
		overflow: hidden;
	}
	::v-deep .el-dialog__footer{
		text-align: center;
	}
</style>