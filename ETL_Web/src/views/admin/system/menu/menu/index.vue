<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @expandChange="expandChange" />
		<el-table ref="tableTree" size="mini" border :data="tableData" :height="tableHeight" row-key="menuId"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange"
			:tree-props="{children: 'children',hasChildren: 'hasChildren'}">
			<el-table-column prop="showMenuName" label="显示名称">
				<template slot-scope="scope">
					<i :class="scope.row.icon"></i>{{scope.row.showMenuName}}
					<el-tag size="mini" v-if="scope.row.isMenu==0">功能</el-tag>
					<el-tag type="success" size="mini" v-if="scope.row.isMenu==1">菜单</el-tag>
					<el-tag type="info" size="mini" v-if="scope.row.isMenu==2">权限</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="menuName" label="菜单名称" width="180">
				<template slot-scope="scope">
					{{scope.row.menuName}}
				</template>
			</el-table-column>
			<el-table-column prop="routePath" label="路由地址" width="250">
				<template slot-scope="scope">
					{{scope.row.routePath}}
				</template>
			</el-table-column>
			<el-table-column prop="pageName" label="页面名称" width="180">
				<template slot-scope="scope">
					{{scope.row.pageName}}
				</template>
			</el-table-column>


			<el-table-column label="菜单配置" width="200" align="center">
				<template slot-scope="scope">
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.isClose==1?'可关闭':'不能关闭'}}</span>
					<span style="color: #E6A23C;padding-right: 5px;"
						v-if="scope.row.isMenu==1">{{scope.row.isExternal==1?'外部菜单':'系统菜单'}}</span>
					<span style="color: #30B08F;">{{scope.row.isExternal==1?'隐藏':'显示'}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="showOrder" label="排序" width="80" align="center">
				<template slot-scope="scope">
					{{scope.row.showOrder}}
				</template>
			</el-table-column>

			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>

			<el-table-column label="操作" width="180" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="updateMenuEnable(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:menu:updateMenuEnable')">
						<template v-if="scope.row.enable==0">启用</template>
						<template v-if="scope.row.enable==1">停用</template>
					</el-button>
					<el-button @click.native.prevent="editRow(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:menu:edit')">
						修改
					</el-button>
					<el-button @click.native.prevent="addChilderMenu(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:menu:add')" v-show="scope.row.isMenu==0">
						添加下级
					</el-button>
					<el-button @click.native.prevent="role(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:menu:add')" v-show="scope.row.isMenu==1">
						权限管理
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:menu:deleteById')">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>

		<eForm ref="eForm" dialogTitle="添加系统菜单" dialogWidth="800px" dialogHeight="520px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改系统菜单" dialogWidth="800px" dialogHeight="520px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />

		<menuRole ref="menuRole" :dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	import menuRole from '../menuRole/index';

	export default {
		name: 'menuIndex',
		components: {
			eHeader,
			eForm,
			edit,
			menuRole
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//菜单名称
					menuName: ''
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			// console.log(JSON.stringify(this.$store.state.global.roles));
			window.addEventListener('resize', this.initTableHeight);

			this.initData(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/menu/menu/menuTree", this.addPageParam(queryParams,
					this
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
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			add() {
				let eForm = this.$refs['eForm'];
				eForm.form.pid = 0;
				eForm.form.menuId = '';
				eForm.form.parentMenuName = '一级菜单';
				eForm.dialogVisible = true;
			},
			addChilderMenu(row) {
				let eForm = this.$refs['eForm'];
				eForm.dialogVisible = true;
				eForm.form.pid = row.menuId;
				eForm.form.parentMenuName = row.menuName;
			},
			role(row) {
				let menuRole = this.$refs['menuRole'];
				menuRole.dialogVisible = true;
				menuRole.queryParams.menuId = row.menuId;
				menuRole.menuId = row.menuId;
				menuRole.menuPath = row.menuPath;
			},
			editRow(row) {
				if (row.allowEdit == 0) {
					this.$message.error('当前系统菜单不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(row) {
				if (row.allowDelete == 0) {
					this.$message.error('当前系统菜单不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行系统菜单吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/menu/menu/deleteById", {
						"menuId": row
							.menuId
					}).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				}).catch((error) => {
					console.log(error);
				});
			},
			updateMenuEnable(row){
				let enable=1;
				let tips="您确定要启用当前菜单吗?";
				if(row.enable==1){
					enable=0;
					tips="您确定要停用当前菜单吗?";
				}
				this.$confirm(tips, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/menu/menu/updateMenuEnable", {
						"menuId": row.menuId,enable:enable
					}).then(
						res => {
							this.eventCallBack(10);
						}).catch(err => {
					});
				}).catch((error) => {
					console.log(error);
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
							message: '系统菜单添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '系统菜单修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '系统菜单删除成功!',
							type: 'success'
						});
						break;
					case 10: //启用或停用
						this.reloadPage();
						this.$message({
							message: '菜单启用/停用成功!',
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
						.offsetHeight - 5;
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
