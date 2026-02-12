<template>
	<div>
		<el-row>
			<el-col :span="3" class="log">
				<img src="~@/assets/logo.png" width="60">
			</el-col>
			<el-col :span="19">
				<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
					:background-color="menuBackgroundColor" :text-color="menuTextColor"
					:active-text-color="menuActiveTextColor">
					<el-menu-item index="0" key="0"  @click="handleRouteIndex">工作空间</el-menu-item>
					<el-menu-item :index="`${index+1}`" v-for="(item,index) in headerMenus" :key="item.id"
						@click="handleRoute(item,`${index+1}`)">{{item.menuName}}</el-menu-item>
				</el-menu>
			</el-col>
			<el-col :span="2"></el-col>
		</el-row>
	</div>
</template>

<script>
	import {getHeaderMenus,setMenuIndex,getMenuIndex} from "@/utils/localStore.js";
	import { mapState } from 'vuex'
	export default {
		name: "headerContainer",
		components: {

		},
		props: {

		},
		computed: {
			...mapState({
				menuBackgroundColor:state=>state.global.menuBackgroundColor,
				menuTextColor:state=>state.global.menuTextColor,
				menuActiveTextColor:state=>state.global.menuActiveTextColor
			}),
		},
		data() {
			return {
				activeIndex: getMenuIndex(),
				headerMenus:getHeaderMenus(),
				menuBg: this.variables.menuBg
			}
		},
		created() {

		},
		methods: {
			handleRouteIndex() {
				setMenuIndex(0);
				this.$router.push({
					path: '/index'
				});
			},
			handleRoute(menu,index) {
				setMenuIndex(index);
				let params = {
					menuId: menu.id
				}
				this.$router.push({
					path: '/operation',
					query: params
				});
			},
			variables() {
				return variables;
			}
		}
	}
</script>

<style>
</style>