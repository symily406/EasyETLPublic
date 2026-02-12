<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" @batchDelete="batchDelete" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="40" align="center" fixed="left">
			</el-table-column>
			<el-table-column prop="companyName" label="企业名称" width="180">
				<template slot-scope="scope">
					{{scope.row.companyName}}
				</template>
			</el-table-column>
			<el-table-column prop="platformName" label="平台名称" width="180">
				<template slot-scope="scope">
					{{scope.row.platformName}}
				</template>
			</el-table-column>
			<el-table-column prop="province" label="详细地址">
				<template slot-scope="scope">
					{{scope.row.provinceText}}{{scope.row.cityText}}{{scope.row.countyText}}{{scope.row.address}}
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
			<el-table-column fixed="right" label="操作" width="180">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:company:edit')">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:company:getById')">
						删除
					</el-button>
					<el-button @click.native.prevent="initCompany(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:company:initCompany')&&scope.row.isInit==0">
						初始化企业
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"/>
		<eForm ref="eForm" dialogTitle="添加企业" dialogWidth="80%" :dialogTop="this.$store.state.global.dialogTop"
			@eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改企业" dialogWidth="80%" :dialogTop="this.$store.state.global.dialogTop"
			@eventCallBack="eventCallBack" />

		<initCompany ref="initCompany" dialogTitle="初始化企业" dialogWidth="80%"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	import initCompany from './module/initCompany';

	export default {
		name: 'companyIndex',
		components: {
			ePage,
			eHeader,
			eForm,
			edit,
			initCompany
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//企业名称
					companyName: '',
					//平台名称
					platformName: '',
					//省
					province: '',
					//市
					city: '',
					//县
					county: '',
					//地址
					address: '',
					//后台首页路径
					indexPath: '',
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
			this.initDate(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			initDate(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/company/queryPage", this.addPageParam(queryParams, this
          .page)).then(res => {
					this.loading = false;
					this.page = this.$app.extend(this.page, this.$app.setPage(res.data));
					this.tableData = res.data.records;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initDate(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.initDate(this.queryParams);
			},
			searchPage() {
				this.initDate(this.queryParams);
			},
			add() {
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前企业不能修改!');
					return false;
				}
				this.initCompanyContent(index, (row) => {
					this.$refs['edit'].edit(row);
				});
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前企业不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行企业吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/company/deleteById", { "companyId": row
							.companyId }).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				}).catch((error) => {
					
				});
			},
			batchDelete() {
				if (this.selectData.length == 0) {
					this.$message.error('请选择要一行要删除的企业!');
					return;
				}
				let allowDelete = true;
				let companyIds = [];
				this.selectData.forEach(item => {
					if (item.allowDelete == 0) {
						allowDelete = false;
					}
					companyIds.push(item.companyId);
				});
				if (allowDelete == 0) {
					this.$message.error('选中的企业有不能被删除的!');
					return false;
				}
				this.$confirm('您确定是否删除选中企业吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
						"/admin/system/company/batchDeleteById", { "companyIds": companyIds.join(",") })
						.then(res => {
							this.eventCallBack(3);
						}).catch(err => {

						});
				}).catch((error) => {
					
				});
			},
			initCompanyContent(index, callBack) {
				const row = this.tableData[index];
				this.$ajax.get(this.getContentPath() + "/admin/system/companyInfo/getById", { "companyId": row.companyId })
					.then(res => {
						row.content = res.data.content;
						callBack(row);
					}).catch(err => {

					});
			},
			//初始化企业
			initCompany(index) {
				const row = this.tableData[index];
				let initCompany = this.$refs['initCompany'];
				initCompany.form.companyId = row.companyId;
				initCompany.dialogVisible = true;
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '企业添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '企业修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '企业删除成功!',
							type: 'success'
						});
						break;
				}
			},
			handleSelectionChange(data) {
				this.selectData = data;
			},
			initTableHeight() {
				if(this.$refs['appContainer'].offsetHeight){
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
