<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="app-container" ref="appContainer">
			<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage"
				@refreshPage="refreshPage" @add="add" @batchDelete="batchDelete" />
			<el-table size="mini" border :data="tableData" :height="tableHeight"
				:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
				v-loading="loading" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="40" align="center" fixed="left">
				</el-table-column>
				<el-table-column prop="executionStatusDesc" label="执行状态" width="120" align="center">
					<template slot-scope="scope">
						{{scope.row.executionStatusDesc}}
					</template>
				</el-table-column>
				<el-table-column prop="executeTime" label="执行时间" width="180" align="center">
					<template slot-scope="scope">
						{{scope.row.executeTime}}
					</template>
				</el-table-column>
				<el-table-column prop="useTime" label="用时" width="100" align="center">
					<template slot-scope="scope">
						{{scope.row.useTime}}毫秒
					</template>
				</el-table-column>
				<el-table-column prop="content" label="执行日志">
					<template slot-scope="scope">
						<div v-html="scope.row.content"></div>
					</template>
				</el-table-column>

			</el-table>
			<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		</div>
	</el-dialog>
</template>
<script>
	import {
		indexMixin,
		dialogMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	export default {
		name: 'taskScheduleLogIndex',
		components: {
			ePage,
			eHeader
		},
		props: {

		},
		mixins: [indexMixin, dialogMixin],
		data() {
			return {
				queryParams: {
					//所属任务
					taskScheduleId: undefined,
					
					//执行状态
					executionStatus: undefined,
				}
			}
		},
		mounted() {

		},
		created() {


		},
		destroyed() {

		},
		methods: {
			openWin() {
				this.$nextTick(() => {
					this.initTableHeight();
				});
				this.tableData=[];
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.queryParams = this.$options.data().queryParams;
				this.tableData=[];
			},
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/logInfo/queryPage", this.addPageParam(
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
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的任务调度日志!');
					return;
				}
				let allowDelete = true;
				let logIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					logIds.push(item.logId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的任务调度日志有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中任务调度日志吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/logInfo/batchDeleteById", {
							"logIds": logIds.join(",")
						})
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '任务调度日志删除成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				this.tableHeight = window.innerHeight - this.$refs['headerContainer'].$el
					.offsetHeight-200;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>