<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading">
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
					<div v-if="scope.row.sourceType==1||scope.row.sourceType==3"><span class="connect-text">用户名：</span>{{scope.row.userName}}</div>
					<div v-if="scope.row.sourceType==2"><span class="connect-text">defaultFS：</span>{{scope.row.defaultFs}}</div>
					<div><span class="connect-text">驱&nbsp;&nbsp;&nbsp;&nbsp;动：</span>{{scope.row.jdbcDrive}}</div>
				</template>
			</el-table-column>

			<el-table-column prop="isConn" label="连接状态" width="100" align="center">
				<template slot-scope="scope">
					<p v-show="scope.row.isConn==1"><i class="el-icon-success connect-succed"></i>成功</p>
					<p v-show="scope.row.isConn==-1"><i class="el-icon-error connect-error"></i>失败</p>
				</template>
			</el-table-column>
			<el-table-column prop="quoteNum" label="引用次数" width="100" align="center">
				<template slot-scope="scope">
					{{scope.row.quoteNum}}
				</template>
			</el-table-column>
			<el-table-column prop="updateTime" label="修改时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.updateTime}}
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:sourceType:dbSource:edit')&&scope.row.allowEdit==1">
						编辑
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:sourceType:dbSource:getById')&&scope.row.allowDelete==1">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改数据源" dialogWidth="1000px" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<eAddDbSource ref="eAddDbSource" dialogTitle="添加数据源" dialogWidth="1000px" dialogHeight="600"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import edit from './module/edit';
	import eAddDbSource from './module/addDbSource';
	export default {
		name: 'dbSourceIndex',
		components: {
			ePage,
			eHeader,
			edit,
			eAddDbSource
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					searchKey: '',
					sourceTypeId:''
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

			this.initData(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
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
			add() {
				this.$refs['eAddDbSource'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前数据源不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前数据源不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行数据源吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
						"/admin/etl/dbSource/deleteById", { "dbSourceId": row.dbSourceId }).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				});
			},
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的数据源!');
					return;
				}
				let allowDelete = true;
				let dbSourceIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					dbSourceIds.push(item.dbSourceId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的数据源有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中数据源吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/etl/dbSource/batchDeleteById", {
								"dbSourceIds": dbSourceIds.join(
									",")
							})
						.then(res => {
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
							message: '数据源添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '数据源修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '数据源删除成功!',
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