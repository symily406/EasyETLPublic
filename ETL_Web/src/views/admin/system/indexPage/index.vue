<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center">
			</el-table-column>
			<el-table-column prop="menuName" label="名称" width="180">
				<template slot-scope="scope">
					{{scope.row.menuName}}
				</template>
			</el-table-column>

			<el-table-column prop="routePath" label="路由地址">
				<template slot-scope="scope">
					{{scope.row.routePath}}
				</template>
			</el-table-column>
			<el-table-column prop="pageName" label="页面名称" width="180">
				<template slot-scope="scope">
					{{scope.row.pageName}}
				</template>
			</el-table-column>
			<el-table-column prop="showOrder" label="排序" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.showOrder}}
				</template>
			</el-table-column>

			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column label="配置" width="180">
				<template slot-scope="scope">
					<span style="color: #ff007f; padding-right: 5px;">{{scope.row.isExternal==1?'外':''}}</span>
					<span style="color: #F56C6C; padding-right: 5px;">{{scope.row.allowEdit==1?'可编辑':'锁定'}}</span>
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.allowDelete==1?'可删除':'不可删'}}</span>
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'未启用'}}</span>
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:indexPage:edit')&&scope.row.allowEdit==1">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:indexPage:getById')&&scope.row.allowDelete==1">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<eForm ref="eForm" dialogTitle="添加" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'systemIndexPageIndex',
		components: {
			ePage,
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
					//上级ID
					pid: '',
					//菜单全路径
					menuPath: '',
					//菜单名称
					menuName: '',
					//菜单显示名称
					showMenuName: '',
					//标题
					title: '',
					//路由地址
					routePath: '',
					//页面名称
					pageName: '',
					//访问地址
					url: '',
					//图标
					icon: '',
					//是否菜单
					isMenu: '',
					//是否可以关闭
					isClose: '',
					//是否外部地址
					isExternal: '',
					//是否隐藏
					isHidden: '',
					//允许编辑
					allowEdit: '',
					//允许删除
					allowDelete: '',
					//启用
					enable: '',
					//删除
					isDel: '',
					//添加时间
					addTime: '',
					//添加人员编号
					addUserId: '',
					//修改时间
					updateTime: '',
					//修改人员编号
					updateUserId: '',
					//版本
					version: '',
					//显示排序
					showOrder: ''
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
				this.$ajax.get(this.getContentPath() + "/admin/system/indexPage/queryPage", this.addPageParam(queryParams,
					this
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
					this.$message.error('当前系统首页不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前系统首页不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行系统首页吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/indexPage/deleteById", {
						"menuId": row
							.menuId
					}).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				});
			},
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的系统首页!');
					return;
				}
				let allowDelete = true;
				let menuIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					menuIds.push(item.menuId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的系统首页有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中系统首页吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/indexPage/batchDeleteById", { "menuIds": menuIds.join(",") })
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
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '系统首页添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '系统首页修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '系统首页删除成功!',
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
