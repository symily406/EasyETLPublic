<template>
	<el-container>
		<sidebar  class="sidebar-container" :style="{top:headerHeight+'px',width: sideBarWidth+'px',height: contentHeight+'px'}">
		</sidebar>
		<mainContainer ref="mainContainer" class="operation-container" :style="{marginLeft:sideBarWidth+'px'}">
		</mainContainer>
	</el-container>
</template>
<script>
	import { mapState } from 'vuex'
	import variables from '@/assets/scss/variables.scss'
	import sidebar from "./modules/sidebar/index.vue";
	import mainContainer from "./modules/main/main.vue";
	import {
		geTabs,
		setTabs
	} from "@/utils/localStore.js";
	export default {
		name: "layout",
		components: {
			sidebar,
			mainContainer
		},
		props: {

		},
		computed:{
			...mapState({
				headerHeight: state=>state.global.headerHeight,
				sideBarWidth: state=>state.global.sideBarWidth,
				contentHeight: state=>state.global.contentHeight
			}),
			mainTabs:{
				get () {
					let tabs=this.$store.state.global.tabs;
					let storeTabs=geTabs();
					if(tabs.length==0&&storeTabs){
						tabs=JSON.parse(storeTabs);
						this.$store.commit("global/global/updateTabs",tabs)
					}
					return tabs;
				},
				set (val) {
					setTabs(JSON.stringify(val));
					this.$store.commit("global/global/updateTabs",val)
				}
			},
			activeTab:{
				get () { return this.$store.state.global.tabIndex },
				set (val) { this.$store.commit("global/global/updateActiveTab",val)}
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
			pageResize(){
				this.$store.commit("global/global/setTableWidth");
			}
		}
	}
</script>
<style scoped="scoped">

</style>