<template>
	<div>
		<el-submenu v-if="menu.children && menu.children.length >= 1" :index="'' + menu.id"
			:popper-append-to-body="true">
			<template slot="title">
				<i :class="menu.icon"></i>
				<span slot="title">{{menu.menuName}}</span>
			</template>
			<menuItem v-for="item in menu.children" :key="item.id" :menu="item">
			</menuItem>
		</el-submenu>
		<el-menu-item v-else :index="'' + menu.id" @click="handleRoute(menu)">
			<i :class="`${menu.icon} menu-icon`"></i>
			<span slot="title">{{menu.menuName}}</span>
		</el-menu-item>
	</div>
</template>
<script>
	export default {
		name: "menuItem",
		components: {

		},
		props: {
			menu: {
				type: Object,
				required: true
			}
		},
		data() {
			return {

			}
		},
		created() {
			
		},
		methods: {
			handleRoute(menu) {
				if (menu.isExternal) {
					this.$router.push({
						path: menu.routePath,
						query:{url:encodeURIComponent(menu.url)}
					});
				} else {
					this.$router.push(menu.routePath);
				}
			}
		}
	}
</script>
