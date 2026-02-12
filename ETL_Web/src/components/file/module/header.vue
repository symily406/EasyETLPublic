<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="归属文件夹">
<el-input v-model="queryParams.folderId" placeholder="归属文件夹" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="归属文件配置">
<el-input v-model="queryParams.configId" placeholder="归属文件配置" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="MD5">
<el-input v-model="queryParams.文件md5" placeholder="MD5" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="文件名">
<el-input v-model="queryParams.fileName" placeholder="文件名" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="保存文件夹">
<el-input v-model="queryParams.saveFolder" placeholder="保存文件夹" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="文件大小">
<el-input v-model="queryParams.fileSize" placeholder="文件大小" size="small" clearable></el-input>
</el-form-item>
<el-form-item label="保存文件名">
<el-input v-model="queryParams.saveName" placeholder="保存文件名" size="small" clearable></el-input>
</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add" v-if="checkPermission('admin:system:file:add')">新增</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete" v-if="checkPermission('admin:system:file:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'fileHeader',
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
