<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="app-container" ref="appContainer">
			<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage"
				@refreshPage="refreshPage" />
			<el-table size="mini" border :data="tableData" :height="600"
				:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
				v-loading="loading" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="40" align="center" fixed="left">
				</el-table-column>
				<el-table-column prop="dbSourceName" label="数据源名称" width="180">
					<template slot-scope="scope">
						{{scope.row.dbSourceName}}
					</template>
				</el-table-column>
				<el-table-column prop="sourceTypeName" label="类型" width="100">
					<template slot-scope="scope">
						{{scope.row.sourceTypeName}}
					</template>
				</el-table-column>
				<el-table-column prop="description" label="描述" width="200">
					<template slot-scope="scope">
						{{scope.row.description}}
					</template>
				</el-table-column>
				<el-table-column prop="jdbcUrl" label="连接信息">
					<template slot-scope="scope">
						<div><span class="connect-text">jdbcUrl：</span>{{scope.row.jdbcUrl}}</div>
						<div v-if="scope.row.sourceType==1||scope.row.sourceType==3"><span
								class="connect-text">用户名：</span>{{scope.row.userName}}</div>
						<div v-if="scope.row.sourceType==2"><span
								class="connect-text">defaultFS：</span>{{scope.row.userName}}</div>
						<div><span class="connect-text">驱&nbsp;&nbsp;&nbsp;&nbsp;动：</span>{{scope.row.jdbcDrive}}</div>
					</template>
				</el-table-column>

				<el-table-column prop="isConn" label="连接状态" width="100" align="center">
					<template slot-scope="scope">
						<p v-show="scope.row.isConn==1"><i class="el-icon-success connect-succed"></i>成功</p>
						<p v-show="scope.row.isConn==-1"><i class="el-icon-error connect-error"></i>失败</p>
					</template>
				</el-table-column>

				<el-table-column prop="updateTime" label="修改时间" width="150" align="center">
					<template slot-scope="scope">
						{{scope.row.updateTime}}
					</template>
				</el-table-column>
			</el-table>
			<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"
				:dialogTop="this.$store.state.global.dialogTop" />
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关闭</el-button>
			<el-button size="small" type="primary" @click="dbSourceSelectCallBack()">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { indexMixin, dialogMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/dbSourceSelectheader';
	export default {
		name: 'dbSourceSelect',
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
					searchKey: '',
					sourceTypeId: '',
					projectId:''
				}
			}
		},
		mounted() {

		},
		methods: {
			openWin() {
				this.selectData = [];
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.queryParams = this.$options.data().queryParams;
			},
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSource/queryPage", this.addPageParam(
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
			dbSourceSelectCallBack() {
				let dbJson = [];
				this.selectData.forEach(item => {
					dbJson.push({
						"projectId": this.queryParams.projectId,
						"dbSourceId": item.dbSourceId
					});
				});
				this.loading = true;
				this.$ajax.post(this.getContentPath() + "/admin/etl/projectDbSource/saveOrUpdate", { "dbJson": JSON
						.stringify(dbJson) }).then(res => {
					this.loading = false;
					const eventType = 1;
					this.selectData = [];
					this.$emit('eventCallBack', eventType);
					this.closeWin();
				}).catch(err => {
					this.loading = false;
				});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.connect-succed {
		color: #00A755;
	}

	.connect-error {
		color: ff0000;
	}

	.connect-text {
		color: #999999;
	}
</style>