<template>
	<el-drawer :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :size="dialogWidth"
		@opened="openWin" @close="closeWin" append-to-body :modal="true">
		<div class="app-container" ref="appContainer">
			<el-table size="mini" border :data="tableData" :height="tableHeight" style="width: 100%;"
				:header-cell-style="{textAlign:'center'}" v-loading="loading" @selection-change="handleSelectionChange">
				<el-table-column prop="JobId" label="时间" width="150" align="center">
					<template slot-scope="scope" sortable>
						{{scope.row.addTime}}
					</template>
				</el-table-column>
				<el-table-column prop="logContent" label="日志">
					<template slot-scope="scope">
						<div v-html="scope.row.logContent.replace(/(\n|\r|\r\n|↵)/g, '<br/>')"></div>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<div :style="{textAlign: 'center',width: dialogWidth, position: 'fixed',bottom: '0px'}">
			<el-button type="primary" size="small" plain icon="el-icon-refresh" @click="refreshPage">刷新日志</el-button>
		</div>
	</el-drawer>
</template>
<script>
	import {
		indexMixin,
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'runTaskJobIndex',
		components: {

		},
		props: {

		},
		mixins: [indexMixin, dialogMixin],
		data() {
			return {
				jobId: undefined,
				queryParams: {
					jobId: undefined,
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
				this.queryParams.jobId = this.jobId;
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.tableData = [];
				this.queryParams = this.$options.data().queryParams;
			},
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJobLog/queryJobLogInfo", this.addPageParam(
					queryParams, this
					.page)).then(res => {
					this.loading = false;
					this.tableData = res.data.logInfo;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.queryParams.jobId = this.jobId;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			initTableHeight() {
				this.tableHeight = window.innerHeight - 120;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>