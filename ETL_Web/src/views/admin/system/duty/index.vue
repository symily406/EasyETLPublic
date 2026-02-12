<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center" fixed="left">
			</el-table-column>
			<el-table-column prop="dutyName" label="岗位名称">
				<template slot-scope="scope">
					{{scope.row.dutyName}}
				</template>
			</el-table-column>
			<el-table-column prop="companyId" label="归属企业" width="180">
				<template slot-scope="scope">
					{{scope.row.companyName}}
				</template>
			</el-table-column>
			<el-table-column prop="orgId" label="归属机构" width="180">
				<template slot-scope="scope">
					{{scope.row.orgName}}
				</template>
			</el-table-column>
			<el-table-column prop="addTime" label="添加时间" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column label="配置" width="150">
				<template slot-scope="scope">
					<span style="color: #F56C6C; padding-right: 5px;">{{scope.row.allowEdit==1?'可编辑':'锁定'}}</span>
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.allowDelete==1?'可删除':'不可删'}}</span>
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'未启用'}}</span>
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:duty:edit')&&scope.row.allowEdit==1">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:duty:getById')&&scope.row.allowDelete==1">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<eForm ref="eForm" dialogTitle="添加岗位" dialogWidth="600px" dialogHeight="250px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改岗位" dialogWidth="600px" dialogHeight="250px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	import ePage from "@/components/page/page";
	export default {
		name: 'dutyIndex',
		components: {
			eHeader,
			eForm,
			edit,
			ePage
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//岗位名称
					dutyName: '',
					//归属企业
					companyId: '',
					//归属机构
					orgId: null
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
				this.$ajax.get(this.getContentPath() + "/admin/system/duty/queryPage", this.addPageParam(queryParams, this
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
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前岗位管理不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前岗位管理不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行岗位管理吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/duty/deleteById", {
						"dutyId": row
							.dutyId
					}).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				}).catch((error) => {

				});
			},
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的岗位管理!');
					return;
				}
				let allowDelete = true;
				let dutyIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					dutyIds.push(item.dutyId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的岗位管理有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中岗位管理吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/duty/batchDeleteById", { "dutyIds": dutyIds.join(",") })
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				}).catch((error) => {

				});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '岗位管理添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '岗位管理修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '岗位管理删除成功!',
							type: 'success'
						});
						break;
				}
			},
			handleSelectionChange(data) {
				this.selectData = data;
			},
			handleSizeChange() {

			},
			handleCurrentChange() {

			},
			initTableHeight() {
				if (this.$refs['appContainer'].offsetHeight) {
					this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
						.offsetHeight - 50;
				}
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
