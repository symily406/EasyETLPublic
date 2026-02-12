<template>
	<div>
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height: contentHeight+'px'}">
			<el-menu :collapse="this.$store.state.global.isCollapse" :background-color="menuBackgroundColor"
				:text-color="menuTextColor" :active-text-color="menuActiveTextColor" :unique-opened="true"
				:collapse-transition="true" :default-active="tabIndex" mode="vertical">
				<menuItem v-for="item in menus" :key="item.id" :menu="item" :menuId="menuId">
				</menuItem>
			</el-menu>
		</el-scrollbar>
	</div>
</template>

<script>
	import {
		getMenus,
		setMenuId
	} from "@/utils/localStore.js";
	import {
		mapState
	} from 'vuex';
	import menuItem from "./menuItem";
	import {
		getPlatformName
	} from "@/utils/localStore.js";
	export default {
		name: "sidebarContainer",
		components: {
			menuItem
		},
		props: {

		},
		computed: {
			...mapState({
				contentHeight: state => state.global.contentHeight,
				menuBackgroundColor: state => state.global.menuBackgroundColor,
				menuTextColor: state => state.global.menuTextColor,
				menuActiveTextColor: state => state.global.menuActiveTextColor
			}),
			tabIndex: {
				get() {
					return this.$store.state.global.tabIndex
				},
				set(val) {
					this.$store.commit("global/global/updateActiveTab", val)
				}
			},
			platformName: {
				get() {
					return getPlatformName()
				}
			}
		},
		mounted() {

		},
		watch: {
			'$route.query.menuId': {
				handler(newVal, oldVal) {
					this.menuId = newVal;
					setMenuId(this.menuId);
					this.initOperationMenus();
				},
				deep: true
			}
		},
		data() {
			return {
				menuId: "0",
				menus: []
			}
		},
		created() {
			this.menuId = this.$route.query.menuId;
			setMenuId(this.menuId);
			this.initOperationMenus();
		},
		methods: {
			initOperationMenus() {
				this.menus = getMenus().filter(item => item.id === this.menuId)[0].children;
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>