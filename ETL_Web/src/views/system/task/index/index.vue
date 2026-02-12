<template>
	<div @click="hideTreeMenu">
		<el-container>
			<table border="0" width="100%" cellspacing="0" callpadding="0">
				<tr>
					<td :style="{width: sideBarWidth+'px'}" valign="top">
						<sidebar class="task-sidebar-container" :style="{width: sideBarWidth+'px'}">
						</sidebar>
					</td>
					<td valign="top">
						<table border="0" width="100%" cellspacing="0" callpadding="0">
							<tr>
								<td>
									<navbar class="task-nav-container"></navbar>
								</td>
							</tr>
							<tr>
								<td>
									<mainContainer ref="mainContainer" class="taks-main-container">
									</mainContainer>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</el-container>
	</div>
</template>
<script>
	import { mapState } from 'vuex'
	import variables from '@/assets/scss/variables.scss'
	import sidebar from "./modules/sidebar/index.vue";
	import navbar from "./modules/navbar/index.vue";
	import mainContainer from "./modules/main/main.vue";

	export default {
		name: "layout",
		components: {
			sidebar,
			navbar,
			mainContainer
		},
		props: {

		},
		computed: {
			...mapState({
				sideBarWidth: state => state.global.sideBarWidth
			}),
			taskMenuShow: {
				get() { return this.$store.state.global.taskMenuShow },
				set(val) { this.$store.commit("global/global/setTaskMenuShow", val) }
			}
		},
		watch: {
			
		},
		data() {
			return {
				
			}
		},
		beforeCreate() {

		},
		mounted() {
			this.$nextTick(() => {
				this.pageResize();
			});
		},
		created() {
			this.$store.commit("global/global/setTableWidth");
			window.addEventListener('resize', this.pageResize);
		},
		destroyed() {
			window.removeEventListener('resize', this.pageResize)
		},
		beforeMount() {

		},
		mounted() {
			
		},
		methods: {
			hideTreeMenu() {
				this.taskMenuShow = false;
			},
			pageResize() {
				this.$store.commit("global/global/setTableWidth");
			}
		}
	}
</script>
<style scoped="scoped">

</style>