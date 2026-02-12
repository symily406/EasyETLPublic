<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column prop="workSpaceName" label="名称">
				<template slot-scope="scope">
					{{scope.row.workSpaceName}}
				</template>
			</el-table-column>
			<el-table-column prop="workSpaceTypeName" label="任务类型" width="100">
				<template slot-scope="scope">
					{{scope.row.workSpaceTypeName}}
				</template>
			</el-table-column>

			
			<el-table-column prop="showOrder" label="显示排序" width="100" align="center">
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
					<span style="color: #F56C6C; padding-right: 5px;">{{scope.row.allowEdit==1?'可编辑':'锁定'}}</span>
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.allowDelete==1?'可删除':'不可删'}}</span>
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'未启用'}}</span>
				</template>
			</el-table-column>

			<el-table-column fixed="right" label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:projectWorkSpace:edit')&&scope.row.allowEdit==1">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:projectWorkSpace:deleteById')&&scope.row.allowDelete==1">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<eForm ref="eForm" dialogTitle="添加工作空间" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改工作空间" dialogWidth="800px" dialogHeight="400px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'projectWorkSpaceIndex',
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
					//名称
					workSpaceName: '',
					//工作空间任务类型
					workSpaceType: ''
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
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectWorkSpace/queryAll", this.addPageParam(
					queryParams, this
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
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前项目工作空间不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前项目工作空间不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行项目工作空间吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectWorkSpace/deleteById", {
						"workSpaceId": row.workSpaceId
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
							message: '项目工作空间添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '项目工作空间修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '项目工作空间删除成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
					.offsetHeight ;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>