<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			:projectId="projectId" @add="add" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column prop="projectName" label="归属项目">
				<template slot-scope="scope">
					{{scope.row.projectName}}
				</template>
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
			<el-table-column fixed="right" label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:projectDbSource:deleteById')">
						删除
					</el-button>

				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eDbSourceSelect ref="eDbSourceSelect" dialogTitle="引用的数据源" dialogWidth="80%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eDbSourceSelect from '../dbSource/dbSourceSelect';
	export default {
		name: 'projectDbSourceIndex',
		components: {
			ePage,
			eHeader,
			eDbSourceSelect
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				projectId: undefined,
				queryParams: {
					projectId: '',
					//归属项目
					projectName: '',
					//数据源
					searchKey: '',
					sourceTypeId: ''
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			if (!this.projectId) {
				window.addEventListener('resize', this.initTableHeight);
				this.initData(this.queryParams);
			}
		},
		destroyed() {
			if (!this.projectId) {
				window.removeEventListener('resize', this.initTableHeight);
			}
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectDbSource/queryPage", this.addPageParam(
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
			add() {
				this.$refs['eDbSourceSelect'].queryParams.projectId = this.projectId;
				this.$refs['eDbSourceSelect'].dialogVisible = true;
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前引用的数据源不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行引用的数据源吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
						"/admin/etl/projectDbSource/deleteById", {
							"quoteId": row.quoteId
						}).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '引用数据源成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '项目管理删除成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				if (!this.projectId) {
					this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
						.offsetHeight - 50;
				}
			},
			getContentPath() {
				return this.$app.global.etlPath;
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