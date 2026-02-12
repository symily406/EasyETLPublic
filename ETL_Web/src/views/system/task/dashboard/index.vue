<template>
	<div class="operation-container">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height: contentHeight+'px'}">
			任务首页666
		</el-scrollbar>
	</div>
</template>
<script>
	import { mapState } from 'vuex'
	export default {
		name: "operationDashboard",
		components: {

		},
		props: {

		},
		computed:{
			...mapState({
				contentHeight: state=>state.global.contentHeight
			}),
		},
		data() {
			return {

			}
		},
		mounted() {

		},
		created() {

		},
		methods: {

		}
	}
</script>