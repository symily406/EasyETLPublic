<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" :projectId="projectId" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column prop="projectName" label="归属项目">
				<template slot-scope="scope">
					{{scope.row.projectName}}
				</template>
			</el-table-column>
			<el-table-column prop="userName" label="用户名" width="180">
				<template slot-scope="scope">
					{{scope.row.userName}}
				</template>
			</el-table-column>
			<el-table-column prop="name" label="姓名" width="180">
				<template slot-scope="scope">
					{{scope.row.name}}
				</template>
			</el-table-column>
			<el-table-column prop="phone" label="电话" width="180">
				<template slot-scope="scope">
					{{scope.row.phone}}
				</template>
			</el-table-column>
			<el-table-column label="配置" width="100" align="center">
				<template slot-scope="scope">
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'禁用'}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:project:edit')&&scope.row.allowEdit==1">修改
					</el-button>
					<el-button @click.native.prevent="setEnableById(scope.$index,0)" type="text" size="small"
						v-if="checkPermission('admin:etl:projectMember:setEnable')&&scope.row.enable==1">
						禁用
					</el-button>
					<el-button @click.native.prevent="setEnableById(scope.$index,1)" type="text" size="small"
						v-if="checkPermission('admin:etl:projectMember:setEnable')&&scope.row.enable==0">
						启用
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eForm ref="eForm" dialogTitle="添加项目成员" dialogWidth="500px" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改项目成员" dialogWidth="500px" dialogHeight="600px"
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
		name: 'projectMemberIndex',
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
				projectId: undefined,
				queryParams: {
					//归属项目
					projectId: undefined
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			if (!this.projectId) {
				window.addEventListener('resize', this.initTableHeight);
				this.initData(this.queryParams);
			}

		},
		destroyed() {
			if (!this.projectId) {
				window.removeEventListener('resize', this.initTableHeight);
			}
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectMember/queryPage", this.addPageParam(queryParams,
					this.page)).then(res => {
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
				this.$refs['eForm'].form.projectId = this.projectId;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前项目成员不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			setEnableById(index, enable) {
				const row = this.tableData[index];
				let tips = "您确定要启用当前用户吗?";
				if (enable == 0) {
					tips = "您确定要禁用当前用户吗?"
				}
				this.$confirm(tips, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectMember/setEnableById", {
						"memberId": row.memberId,
						"enable": enable
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
							message: '项目成员添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '项目成员修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '操作成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				if (!this.projectId) {
					this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
						.offsetHeight - 50;
				}
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>