<template>
	<el-dialog title="权限管理" :visible.sync="dialogVisible" :close-on-click-modal="false" width="50%" @opened="openWin"
		@close="closeWin" :top="dialogTop">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" @beatchAdd="beatchAdd" />
		<el-table size="mini" border :data="tableData" height="600px" style="width: 100%;"
			:header-cell-style="{textAlign:'center'}" v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center">
			</el-table-column>
			<el-table-column prop="roleName" label="权限名称" width="180">
				<template slot-scope="scope">
					{{scope.row.roleName}}
				</template>
			</el-table-column>
			<el-table-column prop="role" label="权限">
				<template slot-scope="scope">
					{{scope.row.role}}
				</template>
			</el-table-column>
			<el-table-column prop="showOrder" label="排序" width="100" align="center">
				<template slot-scope="scope">
					{{scope.row.showOrder}}
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:menuRole:edit')">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:menuRole:deleteById')">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<eForm ref="eForm" dialogTitle="添加菜单权限" dialogWidth="800px" dialogHeight="250px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改菜单权限" dialogWidth="800px" dialogHeight="250px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { indexMixin, dialogMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'menuRoleIndex',
		components: {
			eHeader,
			eForm,
			edit
		},
		props: {
			dialogTop: {
				type: String,
				default: "10px"
			},
		},
		mixins: [indexMixin, dialogMixin],
		data() {
			return {
				menuId: '',
				menuPath: '',
				queryParams: {
					//所属菜单
					menuId: ''
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
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/menu/menuRole/queryAll", this.addPageParam(queryParams, this
          .page)).then(res => {
					this.loading = false;
					this.tableData = res.data;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.queryParams.menuId = this.menuId;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			add() {
				this.$refs['eForm'].dialogVisible = true;
				this.$refs['eForm'].form.menuId = this.menuId;
				this.$refs['eForm'].form.menuPath = this.menuPath;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前菜单权限不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前菜单权限不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行菜单权限吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/menu/menuRole/deleteById", {
						"roleId": row
							.roleId
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
					this.$message.error('请选择要一行要删除的菜单权限!');
					return;
				}
				let allowDelete = true;
				let roleIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					roleIds.push(item.roleId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的菜单权限有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中菜单权限吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/menu/menuRole/batchDeleteById", { "roleIds": roleIds.join(",") })
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				}).catch((error) => {

				});
			},
			beatchAdd() {
				this.$ajax.get(this.getContentPath() + "/admin/system/menu/menuRole/beatchAdd", { "menuId": this.menuId })
					.then(res => {
						this.eventCallBack(4);
					}).catch(err => {

					});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '菜单权限添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '菜单权限修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '菜单权限删除成功!',
							type: 'success'
						});
						break;
					case 4: //批量添加
						this.reloadPage();
						this.$message({
							message: '菜单权限批量添加成功!',
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

			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
