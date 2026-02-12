<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="">
				<el-input v-model="queryParams.menuName" placeholder="菜单名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:system:menu:add')">新增</el-button>
			</el-form-item>
			<el-form-item class="fright"><el-checkbox v-model="expandAll" @change="expandChange">展开全部</el-checkbox></el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'menuHeader',
		components: {

		},
		props: {
			
		},
		mixins: [headerMixin],
		data() {
			return {
				expandAll:false
			}
		},
		created() {

		},
		methods: {
			
			expandChange(val){
				this.$emit("expandChange",this.expandAll);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
