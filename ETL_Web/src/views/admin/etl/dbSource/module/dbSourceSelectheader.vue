<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="">
				<el-input v-model="queryParams.searchKey" placeholder="数据源名称/描述/连接信息" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="queryParams.sourceTypeId" placeholder="数据源类型" clearable size="small">
					<el-option v-for="item in dbSourceTypes" :key="item.sourceTypeId" :label="item.sourceTypeName" :value="item.sourceTypeId">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:etl:sourceType:dbSource:add')">新增</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'dbSourceHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin],
		data() {
			return {
				projectId:'',
				dbSourceTypes: []
			}
		},
		created() {
			let that = this;
			this.$ajax.get(this.getContentPath() + "/admin/etl/dbSourceType/queryAll", {}).then(res => {
				this.dbSourceTypes = res.data;
			}).catch(error => {
				this.loading = false;
			});
		},
		methods: {
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>