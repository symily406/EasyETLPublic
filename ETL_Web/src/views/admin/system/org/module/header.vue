<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item class="fright"><el-checkbox v-model="expandAll" @change="expandChange">展开全部</el-checkbox></el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin } from "@/mixins/mixins";
	export default {
		name: 'orgHeader',
		components: {

		},
		mixins: [headerMixin],
		data() {
			return {
				expandAll:true
			}
		},
		methods:{
			expandChange(val){
				this.$emit("expandChange",this.expandAll);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
