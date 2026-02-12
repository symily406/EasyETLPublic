<template>
	<div class="operation-container">
		<el-card class="box-card">
			<div slot="header" class="tleft">
				<span>服务端</span>
			</div>
			<machine ref="serviceRef"></machine>
		</el-card>
		<el-card class="box-card">
			<div slot="header" class="tleft">
				<span>执行器</span>
			</div>
			<machine ref="executeRef"></machine>
		</el-card>
	</div>
</template>
<script>
	import {
		mapState
	} from 'vuex';
	import machine from './module/machine'
	export default {
		name: "operationDashboard",
		components: {
			machine
		},
		props: {

		},
		mixins: [],
		computed: {
			...mapState({
				contentHeight: state => state.global.contentHeight
			}),
		},
		data() {
			return {

			}
		},
		mounted() {

		},
		created() {
			this.initData();
		},
		methods: {
			initData() {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/machine/queryAll", {}).then(res => {
					this.loading = false;
					this.$refs['serviceRef'].datas = res.data.webs;
					this.$refs['executeRef'].datas = res.data.executes;
				}).catch(error => {
					this.loading = false;
				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>

<style scoped="scoped">

</style>