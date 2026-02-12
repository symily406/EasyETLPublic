<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="归属企业">
				<el-select v-model="queryParams.companyId" placeholder="归属企业" size="small" clearable @change="initOrg" @clear="clearCompany">
					<el-option v-for="item in companys" :key="item.companyId" :label="item.companyName"
						:value="item.companyId"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="归属部门" class="treeSelect">
				<treeSelect v-model="queryParams.orgId" placeholder="归属部门" :multiple="false" :options="orgs"
					noOptionsText="请选择归属企业" style="width: 200px;padding-top: 5px;" />
			</el-form-item>
			<el-form-item label="用户名">
				<el-input v-model="queryParams.userName" placeholder="用户名" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:system:staff:add')">新增</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete"
					v-if="checkPermission('admin:system:staff:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	import treeSelect from '@riophae/vue-treeselect'
	export default {
		name: 'staffHeader',
		components: {
			treeSelect
		},
		props: {
			
		},
		mixins: [headerMixin],
		data() {
			return {
				companys: [],
				orgs: [],
			}
		},
		created() {
			this.initCompany();
		},
		methods: {
			clearCompany(){
				this.orgId = null;
				this.orgs =[];
				this.queryParams.orgId=null;
			},
			initCompany() {
				this.$ajax.get(this.getContentPath() +
						"/admin/system/company/queryCompanyDropDown", {})
					.then(res => {
						this.companys = res.data;
					}).catch(err => {

					});
			},
			initOrg() {
				if (this.queryParams.companyId) {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/org/queryOrgDropDown", { companyId: this.queryParams.companyId })
						.then(res => {
							this.orgId = null;
							this.orgs = res.data;
						}).catch(err => {

						});
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
