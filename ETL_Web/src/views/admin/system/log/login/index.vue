<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			 @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center">
			</el-table-column>
			<el-table-column prop="name" label="名称" width="150">
				<template slot-scope="scope">
					{{scope.row.name}}
				</template>
			</el-table-column>
			<el-table-column prop="mode" label="请求方式" width="80">
				<template slot-scope="scope">
					{{scope.row.mode}}
				</template>
			</el-table-column>
			<el-table-column prop="url" label="请求地址">
				<template slot-scope="scope">
					{{scope.row.url}}
				</template>
			</el-table-column>
			<el-table-column prop="controller" label="操作方法">
				<template slot-scope="scope">
					{{scope.row.controller}}
				</template>
			</el-table-column>
			<el-table-column prop="ip" label="IP地址" width="100" align="center">
				<template slot-scope="scope">
					{{scope.row.ip}}
				</template>
			</el-table-column>
			<el-table-column prop="usageTime" label="用时" width="100">
				<template slot-scope="scope">
					{{scope.row.usageTime}}毫秒
				</template>
			</el-table-column>

			<el-table-column prop="addTime" label="操作时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			
			<el-table-column label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="viewRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:log:login:view')">
						查看
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eView ref="eView" dialogTitle="查看操作日志" dialogWidth="60%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eView from './module/view';
	export default {
		name: 'loginLog',
		components: {
			ePage,
			eHeader,
			eView
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//名称
					name: '',
					//请求方式
					mode: '',
					//开始时间
					startDate:'',
					//结束时间
					endDate:''
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
				this.$ajax.get(this.getContentPath() + "/admin/system/operationLog/queryLoginPage", this.addPageParam(
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
					this.$message.error('请选择要一行要删除的操作日志!');
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
					this.$message.error('选中的操作日志有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中操作日志吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
						"/admin/system/operationLog/batchDeleteById", { "logIds": logIds.join(",") })
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				});
			},
			viewRow(index) {
				const row = this.tableData[index];
				const eView = this.$refs['eView'];
				eView.dialogVisible = true;
				eView.form = this.$app.initFormData(eView.form, row);
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '操作日志删除成功!',
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
</style>
