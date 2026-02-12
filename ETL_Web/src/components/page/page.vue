<template>
	<div class="page-container" id="page">
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" background
			:current-page.sync="page.pageNumber" :page-sizes="page.pageSizes" :page-size="page.pageSize"
			layout="sizes, prev, pager, next" :total="page.total">
		</el-pagination>
	</div>
</template>
<script>
	export default {
		name: 'page',
		props: {
			page: {
				type: Object,
				required: true
			}
		},
		data() {
			return {

			}
		},
		created() {

		},
		methods: {
			handleSizeChange(pageSize) {
				this.$emit('handleSizeChange',pageSize);
			},
			handleCurrentChange(pageNumber) {
				this.$emit('handleCurrentChange',pageNumber);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
