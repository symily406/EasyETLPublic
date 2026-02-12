<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" width="50%"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" height="600px" style="width: 100%;"
			:header-cell-style="{textAlign:'center'}" v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center">
			</el-table-column>
			<el-table-column prop="name" label="名称">
				<template slot-scope="scope">
					{{scope.row.name}}
				</template>
			</el-table-column>
			<el-table-column prop="value" label="值" width="200">
				<template slot-scope="scope">
					{{scope.row.value}}
				</template>
			</el-table-column>
			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column prop="showOrder" label="排序" width="100" align="center">
				<template slot-scope="scope">
					{{scope.row.showOrder}}
				</template>
			</el-table-column>
			<el-table-column label="配置" width="150">
				<template slot-scope="scope">
					<span style="color: #F56C6C; padding-right: 5px;">{{scope.row.allowEdit==1?'可编辑':'锁定'}}</span>
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.allowDelete==1?'可删除':'不可删'}}</span>
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'未启用'}}</span>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:dictValues:edit')">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:dictValues:deleteById')">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<eForm ref="eForm" dialogTitle="添加字典值" dialogWidth="600px" dialogHeight="300px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改字典值" dialogWidth="600px" dialogHeight="300px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</el-dialog>
</template>
<script>
	import { indexMixin, dialogMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'dictValuesIndex',
		components: {
			ePage,
			eHeader,
			eForm,
			edit
		},
		props: {

		},
		mixins: [indexMixin, dialogMixin],
		data() {
			return {
				dictCode: '',
				queryParams: {
					//归属字典编码
					dictCode: '',
					//编码
					code: '',
					//名称
					name: ''
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
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			initData(queryParams) {
				queryParams.dictCode = this.dictCode;
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/dict/dictValues/queryPage", this.addPageParam(
					queryParams, this
					.page)).then(
					res => {
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
				let eForm = this.$refs['eForm'];
				eForm.form.dictCode = this.dictCode;
				eForm.dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前字典值不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前字典值不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行字典值吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/dict/dictValues/deleteById", {
							"id": row
								.id
						})
						.then(
							res => {
								this.eventCallBack(3);
							}).catch(err => {

						});
				}).catch((error) => {

				});
			},
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的字典值!');
					return;
				}
				let allowDelete = true;
				let ids = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					ids.push(item.id);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的字典值有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中字典值吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/dict/dictValues/batchDeleteById", {
							"ids": ids
								.join(",")
						})
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
							message: '字典值添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '字典值修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '字典值删除成功!',
							type: 'success'
						});
						break;
				}
			},
			handleSelectionChange(data) {
				this.selectData = data;
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
