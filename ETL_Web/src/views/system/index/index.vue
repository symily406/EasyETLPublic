<template>
	<el-container>
		<eHeader class="header-container"></eHeader>
		<eContent class="content-container" :style="{top:headerHeight+'px',height: contentHeight+'px'}"></eContent>
	</el-container>
</template>
<script>
	import { mapState } from 'vuex'
	import variables from '@/assets/scss/variables.scss'
	import eHeader from "./modules/index/header.vue";
	import eContent from "./modules/index/content.vue";
	import {geTabs,setTabs} from "@/utils/localStore.js";
	export default {
		name: "layout",
		components: {
			eHeader,
			eContent
		},
		props: {
			
		},
		computed:{
			...mapState({
				headerHeight: state=>state.global.headerHeight,
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
				if(route.meta.isTask){
					return;
				}
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
