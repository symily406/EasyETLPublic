<template>
  <div>
	<div class="menu-logo">{{platformName}}</div>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
      	      :collapse="this.$store.state.global.isCollapse"
              :background-color="variables.menuBg"
              :text-color="variables.menuText"
              :unique-opened="true"
              :active-text-color="variables.menuActiveText"
              :collapse-transition="true"
			  :default-active="tabIndex"
              mode="vertical"
            >
        <menuItem v-for="item in menus" :key="item.id" :menu="item"></menuItem>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
    import menuItem from "./menuItem"
	import variables from '@/assets/scss/variables.scss';
	import {
		getPlatformName
	} from "@/utils/localStore.js";
	export default {
		name:"sidebarContainer",
		components: {
			menuItem
		},
		props: {

		},
		computed:{
			tabIndex:{
				get () { return this.$store.state.global.tabIndex },
				set (val) { this.$store.commit("global/global/updateActiveTab",val)}
			},
			platformName:{
				get () { return getPlatformName()}
			}
		},
		data() {
			return {
				menus:this.$store.state.global.menus
			}
		},
		created() {
			
		},
		methods: {
			activeMenu() {
				
			},
			variables() {
				return variables
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
