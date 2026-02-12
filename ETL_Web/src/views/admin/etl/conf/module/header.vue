<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="配置名称">
				<el-input v-model="queryParams.confName" placeholder="配置名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:etl:conf:add')">新增</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import {
		headerMixin
	} from "@/mixins/mixins";
	export default {
		name: 'confHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin],
		data() {
			return {

			}
		},
		created() {

		},
		methods: {

		}
	}
</script>
<style scoped="scoped">
</style>