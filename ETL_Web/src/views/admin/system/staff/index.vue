<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center" fixed="left">
			</el-table-column>
			<el-table-column prop="userName" label="用户名">
				<template slot-scope="scope">
					{{scope.row.userName}}
				</template>
			</el-table-column>
			<el-table-column prop="name" label="用户姓名" width="180">
				<template slot-scope="scope">
					{{scope.row.name}}
				</template>
			</el-table-column>
			<el-table-column prop="sex" label="性别" width="80" align="center">
				<template slot-scope="scope">
					{{scope.row.sex=='1'?'男':'女'}}
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
			<el-table-column prop="loginNum" label="登陆次数" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.loginNum}}
				</template>
			</el-table-column>
			<el-table-column prop="expireTime" label="过期时间" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.expireTime}}
				</template>
			</el-table-column>
			<el-table-column prop="isLock" label="锁定" width="180" align="center">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.lock" active-color="#ff4949" inactive-color="#13ce66" size="small"
						inactive-value="0" active-value="1" inactive-text="正常" active-text="锁定"
						@change="modifyUserLock(scope.$index)"></el-switch>
				</template>
			</el-table-column>

			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:staff:edit')">
						修改
					</el-button>
					<el-button @click.native.prevent="modifyPassword(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:staff:edit')">
						修改密码
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:staff:getById')">
						删除
					</el-button>

				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<eForm ref="eForm" dialogTitle="添加用户" dialogWidth="50%" dialogHeight="605px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改用户" dialogWidth="50%" dialogHeight="605px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />

		<modifyPassword ref="modifyPassword" dialogTitle="修改密码" dialogWidth="500px" dialogHeight="200px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	import modifyPassword from './module/modify_password';
	export default {
		name: 'staffIndex',
		components: {
			ePage,
			eHeader,
			eForm,
			edit,
			modifyPassword
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//用户名
					userName: '',
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
				this.$ajax.get(this.getContentPath() + "/admin/system/staff/queryPage", this.addPageParam(queryParams, this
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
					this.$message.error('当前用户不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前用户不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行用户吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/staff/deleteById", {
						"staffId": row
							.staffId
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
					this.$message.error('请选择要一行要删除的用户!');
					return;
				}
				let allowDelete = true;
				let staffIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					staffIds.push(item.staffId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的用户有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中用户吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/staff/batchDeleteById", { "staffIds": staffIds.join(",") })
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				}).catch((error) => {

				});
			},
			initCompany() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/company/queryCompanyDropDown", {})
					.then(res => {

					}).catch(err => {

					});
			},
			modifyUserLock(index) {
				const row = this.tableData[index];
				if (row.isLock == 1) {
					row.isLock = 0;
				} else {
					row.isLock = 1;
				}
				this.$ajax.get(this.getContentPath() +
						"/admin/system/staff/modifyUserLock", { "staffId": row.staffId, "isLock": row.isLock })
					.then(res => {

					}).catch(err => {

					});
			},
			modifyPassword(index) {
				const row = this.tableData[index];
				const modifyPassword = this.$refs['modifyPassword'];
				modifyPassword.form.staffId = row.staffId;
				modifyPassword.dialogVisible = true;
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '用户添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '用户修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '用户删除成功!',
							type: 'success'
						});
						break;
					case 20: //修改密码
						this.$message({
							message: '密码修改成功!',
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
