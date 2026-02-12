<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@expandChange="expandChange" />
		<el-table ref="tableTree" size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" :tree-props="{children: 'children',hasChildren: 'hasChildren'}" default-expand-all
			row-key="id">
			<el-table-column prop="name" label="名称">
				<template slot-scope="scope">
					{{scope.row.name}}
					<el-tag size="mini" v-if="scope.row.leval==1">企业</el-tag>
					<el-tag type="success" size="mini" v-if="scope.row.leval==2">部门</el-tag>
					<el-tag type="danger" size="mini" v-if="scope.row.leval==3">角色</el-tag>
				</template>
			</el-table-column>

			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
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
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<template v-if="scope.row.leval==2">
						<el-button type="text" size="small" @click.native.prevent="add(scope.row)"
							v-if="checkPermission('admin:system:role:add')">
							添加角色
						</el-button>
					</template>
					<template v-if="scope.row.leval==3">
						<el-button @click.native.prevent="editRow(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:role:edit')">
							修改
						</el-button>
						<el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:role:getById')">
							删除
						</el-button>
					</template>
				</template>
			</el-table-column>
		</el-table>

		<eForm ref="eForm" dialogTitle="添加角色" dialogWidth="600px" :dialogTop="this.$store.state.global.dialogTop"
			@eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改角色" dialogWidth="600px" :dialogTop="this.$store.state.global.dialogTop"
			@eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'roleIndex',
		components: {
			eHeader,
			eForm,
			edit
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					
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
				this.$ajax.get(this.getContentPath() + "/admin/system/role/queryAll", queryParams).then(res => {
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
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			add(row) {
				this.$refs['eForm'].form.companyId = row.companyId;
				this.$refs['eForm'].form.orgId = row.orgId;
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(row) {
				if (row.allowEdit == 0) {
					this.$message.error('当前角色不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(row) {
				if (row.allowDelete == 0) {
					this.$message.error('当前角色不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行角色吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/role/deleteById", { "rId": row.id })
						.then(
							res => {
								this.eventCallBack(3);
							}).catch(err => {

						});
				}).catch((error) => {

				});
			},
			expandChange(expandAll) {
				let obj = this.$refs['tableTree'];
				this.$app.tableTreeExpand(obj, this.tableData, expandAll);
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '角色添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '角色修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '角色删除成功!',
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
					this.tableHeight = this.$refs['appContainer'].offsetHeight - 10;
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
