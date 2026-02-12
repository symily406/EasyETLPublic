<template>
	<el-container>
		<sidebar  class="sidebar-container" :style="{width: sideBarWidth+'px'}">
		</sidebar>
		<navbar class="nav-container"
			:style="{marginLeft:sideBarWidth+'px',paddingRight:sideBarWidth+'px'}">
		</navbar>
		<mainContainer ref="mainContainer" class="main-container" :style="{marginLeft:sideBarWidth+'px'}">
		</mainContainer>
	</el-container>
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
		computed:{
			...mapState({
				sideBarWidth: state=>state.global.sideBarWidth
			}),
			mainTabs:{
				get () {
					let tabs=this.$store.state.global.tabs;
					let storeTabs=sessionStorage.getItem("tabs");
					if(tabs.length==0&&storeTabs){
						tabs=JSON.parse(storeTabs);
						this.$store.commit("global/global/updateTabs",tabs)
					}
					return tabs;
				},
				set (val) {
					sessionStorage.setItem("tabs",JSON.stringify(val));
					this.$store.commit("global/global/updateTabs",val)
				}
			},
			activeTab:{
				get () { return this.$store.state.global.tabIndex },
				set (val) { this.$store.commit("global/global/updateActiveTab",val)}
			}
		},
		watch: {
			$route: 'handleRoute'
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
			 this.handleRoute(this.$route);
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
			handleRoute(route) {
				var tab = this.mainTabs.filter(item => item.id === route.meta.id)[0];
				if (!tab) {
				  tab = {
					id:route.meta.id,
					pid:route.meta.pid,
				    menuName: route.meta.menuName,
				    title: route.meta.title,
				    routePath: route.meta.routePath,
					isClose:route.meta.isClose,
					url:route.meta.url,
					isExternal:route.meta.isExternal
				  }
				  this.mainTabs = this.mainTabs.concat(tab);
				}
				this.activeTab=tab.id;
				this.$store.state.global.navs=[];
				this.addTabs(tab.id);
			},
			addTabs(id){
				let menu=this.$store.state.global.allMenus.filter(item=>item.id===id)[0];
				if(menu){
					this.$store.state.global.navs.push(menu);
					if(menu.pid){
						this.addTabs(menu.pid);
					}
				}
			},
			pageResize(){
				this.$store.commit("global/global/setTableWidth");
			}
		}
	}
</script>
<style scoped="scoped">

</style>
