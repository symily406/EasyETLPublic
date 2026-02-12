<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column prop="projectName" label="项目名称">
				<template slot-scope="scope">
					{{scope.row.projectName}}
				</template>
			</el-table-column>
			<el-table-column prop="dbSourceNum" label="数据源" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.dbSourceNum}}
				</template>
			</el-table-column>

			<el-table-column prop="totalTask" label="总任务数" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.totalTask}}
				</template>
			</el-table-column>

			<el-table-column prop="failTask" label="失败任务数" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.failTask}}
				</template>
			</el-table-column>

			<el-table-column prop="tableNum" label="表数量" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.tableNum}}
				</template>
			</el-table-column>

			<el-table-column prop="storageSpace" label="存储空间" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.storageSpace}}
				</template>
			</el-table-column>


			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>

			<el-table-column label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-dropdown>
						<span class="el-dropdown-link">
							操作<i class="el-icon-arrow-down el-icon--right"></i>
						</span>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item @click.native.prevent="dbSourceManager(scope.row)"
								v-if="checkPermission('admin:etl:projectDbSource:queryPage')">数据源管理</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="projectMemberManager(scope.row)"
								v-if="checkPermission('admin:etl:projectMember:queryPage')">成员管理</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="editRow(scope.$index)"
								v-if="checkPermission('admin:etl:project:edit')&&scope.row.allowEdit==1">修改</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="deleteRow(scope.$index)"
								v-if="checkPermission('admin:etl:project:getById')&&scope.row.allowDelete==1">删除</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eForm ref="eForm" dialogTitle="添加项目" dialogWidth="500px" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改项目" dialogWidth="500px" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<dbSourceManager ref="dbSourceManagerRef" dialogTitle="数据源管理" dialogWidth="90%" dialogHeight="600"
			:dialogTop="this.$store.state.global.dialogTop"></dbSourceManager>
		<projectMemberManager ref="projectMemberManagerRef" dialogTitle="成员管理" dialogWidth="90%" dialogHeight="600"
			:dialogTop="this.$store.state.global.dialogTop"></projectMemberManager>
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
	import dbSourceManager from './module/dbSourceManager'
	import projectMemberManager from './module/projectMemberManager'
	export default {
		name: 'projectIndex',
		components: {
			ePage,
			eHeader,
			eForm,
			edit,
			dbSourceManager,
			projectMemberManager
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//项目名称
					projectName: ''
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
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryPage", this.addPageParam(queryParams, this
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
				this.$refs['eForm'].form.projectId = this.$md5(this.$app.uuid());
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前项目管理不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			dbSourceManager(row) {
				this.$refs['dbSourceManagerRef'].projectId = row.projectId;
				this.$refs['dbSourceManagerRef'].dialogVisible = true;
			},
			projectMemberManager(row) {
				this.$refs['projectMemberManagerRef'].projectId = row.projectId;
				this.$refs['projectMemberManagerRef'].dialogVisible = true;
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前项目管理不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行项目管理吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/etl/project/deleteById", {
						"projectId": row
							.projectId
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
							message: '项目管理添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '项目管理修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '项目管理删除成功!',
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
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.el-dropdown-link {
		cursor: pointer;
		color: #409EFF;
	}

	.el-icon-arrow-down {
		font-size: 12px;
	}
</style>