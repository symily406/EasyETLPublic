<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="名称">
				<el-input v-model="queryParams.name" placeholder="名称" size="small" clearable></el-input>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:system:fileConfig:add')">新增</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete"
					v-if="checkPermission('admin:system:fileConfig:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'fileConfigHeader',
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
