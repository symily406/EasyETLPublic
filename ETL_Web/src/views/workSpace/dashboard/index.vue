<template>
	<div class="operation-container">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height: contentHeight+'px'}">
			数据同步aadad 6611
		</el-scrollbar>
	</div>
</template>
<script>
	import { mapState } from 'vuex'
	export default {
		name: "dataIntegrationDashboard",
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