<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:system:menuRole:add')">新增</el-button>
				<el-button type="primary" size="small" icon="el-icon-edit-outline" @click="beatchAdd"
					v-if="checkPermission('admin:system:menuRole:beatchAdd')">批量新增</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete"
					v-if="checkPermission('admin:system:menuRole:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'menuRoleHeader',
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
			beatchAdd(){
				this.$emit("beatchAdd")
			}
		}
	}
</script>
<style scoped="scoped">
</style>
