<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@expandChange="expandChange" />
		<el-table ref="tableTree" size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" :tree-props="{children: 'children',hasChildren: 'hasChildren'}" default-expand-all
			row-key="id">
			<el-table-column prop="orgFullName" label="全称">
				<template slot-scope="scope">
					{{scope.row.fullName}}
					<el-tag size="mini" v-if="scope.row.isOrg==1">企业</el-tag>
					<el-tag type="success" size="mini" v-if="scope.row.isOrg==2">部门</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="companyId" label="简称" width="300">
				<template slot-scope="scope">
					{{scope.row.name}}
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
			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="200" align="center">
				<template slot-scope="scope">
					<span v-if="scope.row.isOrg==1">
						<el-button @click.native.prevent="add(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:org:add')">
							添加部门
						</el-button>
					</span>
					<span v-if="scope.row.isOrg==2">
						<el-button @click.native.prevent="add(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:org:add')">
							添加部门
						</el-button>
						<el-button @click.native.prevent="editRow(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:org:edit')">
							修改
						</el-button>
						<el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small"
							v-if="checkPermission('admin:system:org:getById')">
							删除
						</el-button>
					</span>
				</template>
			</el-table-column>
		</el-table>
		<eForm ref="eForm" dialogTitle="添加机构" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改机构" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'orgIndex',
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
				this.$ajax.get(this.getContentPath() + "/admin/system/org/queryPage", queryParams).then(res => {
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
				this.$refs['eForm'].dialogVisible = true;
				this.$refs['eForm'].form.companyId = row.companyId;
				if (row.isOrg == 1) {
					this.$refs['eForm'].form.pOrgId = '0';
				} else {
					this.$refs['eForm'].form.pOrgId = row.id;
				}
			},
			editRow(row) {
				if (row.allowEdit == 0) {
					this.$message.error('当前机构不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(row) {
				if (row.allowDelete == 0) {
					this.$message.error('当前机构不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行机构吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/org/deleteById", { "orgId": row.id })
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
							message: '机构添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '机构修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '机构删除成功!',
							type: 'success'
						});
						break;
				}
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
