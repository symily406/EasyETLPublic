import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store/index.js";
import {
	setHeaderMenus,
	setMenus,
	setMenuData,
	getMenuData,
	getPermissions,
	setPermissions,
	setFileAccept,
	getRoutePath,
	getIsExternal,
	getUrl,
	geTabs,
	setTabs,
	clearAll
} from "@/utils/localStore.js";

Vue.use(VueRouter);

const whiteList = ['/login', '/auth-redirect'];


let constantRoutes = [{
		path: "/login",
		redirect: "/"
	},
	{
		path: "/",
		name: "login",
		component: () => import("@/views/system/login/index.vue"),
	},
	{
		path: "/index",
		name: "index",
		component: () => import("@/views/system/index/index.vue"),
		children: [{
			path: "/",
			name: "dashboard",
			component: () => import("@/views/system/dashboard/index.vue"),
			meta: {
				id: "0",
				pid: "",
				menuName: "首页",
				title: "",
				routePath: "/index",
				isClose: false,
				url: "",
				isExternal: false
			}
		}, {
			path: "/operation",
			name: "operation",
			component: () => import("@/views/system/operation/index/index.vue"),
			children: [{
				path: "/",
				name: "operationDashboard",
				component: () => import("@/views/system/operation/dashboard/index.vue"),
				meta: {
					id: "0",
					pid: "",
					menuName: "首页",
					title: "",
					routePath: "/dashboardIndex",
					isClose: false,
					url: "",
					isExternal: false
				}
			}]
		}]
	} , {
		path: "/workSpace/dataIntegration",
		name: "workSpaceDataIntegration",
		component: () => import("@/views/workSpace/dataIntegration/index/index.vue"),
		children: [{
			path: "/",
			name: "dataIntegrationDashboard",
			component: () => import("@/views/workSpace/dataIntegration/dashboard/index.vue"),
			children: [],
			meta: {
				id: "1000",
				pid: "",
				menuName: "数据集成",
				title: "",
				routePath: "/dataIntegrationDashboard",
				isClose: true,
				url: "",
				isExternal: false,
				isTask: true
			}
		}]
	}, {
		path: "/workSpace/index",
		name: "workSpaceIndex",
		desc: "工作空间路由",
		component: () => import("@/views/workSpace/index/index.vue"),
		children: [{
			path: "/",
			name: "workSpaceTask",
			desc: "工作空首页",
			component: () => import("@/views/workSpace/task/index/index.vue"),
			meta: {
				id: "1000",
				pid: "",
				menuName: "数据集成",
				title: "",
				routePath: "/workSpaceTask",
				isClose: true,
				url: "",
				isExternal: false,
				isTask: true
			}
		}]
	}, {
		path: "/workSpace/index/dashboard",
		name: "workSpaceSaskDashboard",
		component: () => import("@/views/workSpace/task/dashboard/index.vue"),
		desc: "工作空间面板"
	}, {
		path: "/workSpace/task/dataxTask",
		name: "dataxTask",
		component: () => import("@/views/admin/etl/projectTask/dataxTask.vue"),
		desc: "datax同步任务"
	}, {
		path: "/workSpace/task/sqlTask",
		name: "sqlTask",
		component: () => import("@/views/admin/etl/projectTask/sqlTask.vue"),
		desc: "sql脚本任务"
	}, {
		path: "/workSpace/task/flowTask",
		name: "flowTask",
		component: () => import("@/views/admin/etl/projectTask/flowTask.vue"),
		desc: "工作流"
	}
];

const createRouter = () => new VueRouter({
	// mode: 'history', // require service support
	scrollBehavior: () => ({
		y: 0
	}),
	routes: constantRoutes
});

let router = createRouter();

export function resetRouter() {
	let newRouter = createRouter()
	router.matcher = newRouter.matcher // reset router
}


router.beforeEach((to, from, next) => {
	if (to.path === '/') {
		clearAllStore();
		next();
	} else if (to.path === '/login') {
		clearAllStore();
		next({
			path: '/'
		});
	} else {
		let {
			isExternal,
			url
		} = initIndexPage();
		const hasRoles = store.state.global.roles && store.state.global.roles.length > 0;
		if (hasRoles) {
			next();
		} else {
			store.dispatch("system/menu/initMenu", {}).then(res => {
				setFileAccept(res.data.fileAccept);
				//生成菜单
				let asyMenu = initMenu(res.data.menus);
				store.state.global.menus = asyMenu;
				//动态添加路由
				let asyRoutes = addDynamicRoutes(res.data.menus);
				constantRoutes[2].children[1].children = constantRoutes[2].children[1].children.concat(
					asyRoutes);
				// router.addRoutes(constantRoutes);
				resetRouter();
				addIndexTab();
				//添加权限
				initRole(res.data.permissions);
				if (parseInt(isExternal) == 1) {
					next({
						...to,
						replace: false,
						query: {
							url: encodeURIComponent(url)
						}
					});
				} else {
					next({
						...to,
						replace: false
					});
				}
			}).catch(error => {
				clearAllStore();
				console.log(error);
				next({
					path: '/login'
				});
			});
		}
	}
});

/**
 * 初始化管理员首页
 */
function initIndexPage() {
	let routePath = getRoutePath();
	let isExternal = getIsExternal();
	let url = getUrl();
	if (routePath) {
		if (parseInt(isExternal) == 1) { //外部地址
			constantRoutes[2].children[0] = {
				path: "/",
				name: "dashboard",
				component: () => import('@/views/system/index/modules/iframe/index.vue'),
				meta: {
					id: "0",
					pid: "",
					menuName: "首页",
					title: "",
					routePath: "/index",
					isClose: false,
					url: url,
					isExternal: true
				}
			};
		} else {
			constantRoutes[2].children[0] = {
				path: "/",
				name: "dashboard",
				component: () => import(`@/views${routePath}/index.vue`),
				meta: {
					id: "0",
					pid: "",
					menuName: "首页",
					title: "",
					routePath: "/index",
					isClose: false,
					url: "",
					isExternal: false
				}
			};
		}
	} else {
		constantRoutes[2].children[0] = {
			path: "/",
			name: "dashboard",
			component: () => import("@/views/system/dashboard/index.vue"),
			meta: {
				id: "0",
				pid: "",
				menuName: "首页",
				title: "",
				routePath: "/index",
				isClose: false,
				url: "",
				isExternal: false
			}
		};
	}
	return {
		"isExternal": isExternal,
		"url": url
	};
}

/**
 * 添加首页tab
 */
function addIndexTab() {
	let tabs = store.state.global.tabs;
	let storeTabs = geTabs();
	if (tabs.length == 0 && storeTabs) {
		tabs = JSON.parse(storeTabs);
		store.commit("global/global/updateTabs", tabs)
	}
	let mainTabs = [];
	let tab = constantRoutes[2].children[0].meta;
	mainTabs.push(tab);
	tabs.forEach(item => {
		if (item.id != tab.id) {
			mainTabs.push(item);
		}
	});
	setTabs(JSON.stringify(mainTabs));
	store.commit("global/global/updateTabs", mainTabs)
}

function initMenu(menuList = []) {
	let menus = [];
	menuList.forEach(item => {
		if (!item.isHidden) {
			if (item.isMenu) {
				menus.push({
					id: item.id,
					pid: item.pid,
					menuName: item.menuName,
					title: item.title,
					icon: item.icon,
					routePath: item.routePath,
					isClose: item.isClose,
					url: item.url,
					isExternal: item.isExternal
				});
				store.state.global.searchRouter.push({
					id: item.id,
					pid: item.pid,
					menuName: item.menuName,
					title: item.title,
					routePath: item.routePath,
					isClose: item.isClose,
					url: item.url,
					isExternal: item.isExternal
				});
			} else {
				menus.push({
					id: item.id,
					pid: item.pid,
					menuName: item.menuName,
					title: item.title,
					icon: item.icon,
					children: initMenu(item.children)
				});
			}
		}
		//添加所有菜单
		initAllMenus({
			id: item.id,
			pid: item.pid,
			menuName: item.menuName,
			title: item.title,
			icon: item.icon,
			routePath: item.routePath,
			isClose: item.isClose,
			url: item.url,
			isExternal: item.isExternal
		});
		initHeaderMenu(item);
	});
	setHeaderMenus(store.state.global.headerMenus);
	setMenus(menus);
	return menus;
}

function initHeaderMenu(menu) {
	if (menu.pid == 0) {
		store.state.global.headerMenus.push(menu);
	}
}

//添加权限
function initRole(roles) {
	roles.forEach(role => {
		if (store.state.global.roles.indexOf(role) == -1) {
			store.state.global.roles.push(role);
		}
	});
}
//添加所有菜单
function initAllMenus(menu) {
	let itemMenu = store.state.global.allMenus.filter(item => item.id === menu.id)[0];
	if (!itemMenu) {
		store.state.global.allMenus.push(menu);
	}
}

//初始化路由
function addDynamicRoutes(menuList = []) {
	let routes = [];
	menuList.forEach(item => {
		if (item.isMenu) {
			if (item.isExternal) { //外部菜单
				routes.push({
					path: item.routePath,
					name: item.pageName,
					component: () => import('@/views/system/index/modules/iframe/index.vue'),
					meta: {
						id: item.id,
						pid: item.pid,
						menuName: item.menuName,
						title: item.title,
						routePath: item.routePath,
						isClose: item.isClose,
						url: item.url,
						isExternal: item.isExternal
					}
				});
			} else {
				routes.push({
					path: item.routePath,
					name: item.pageName,
					component: () => import(`@/views${item.routePath}/index.vue`),
					meta: {
						id: item.id,
						pid: item.pid,
						menuName: item.menuName,
						title: item.title,
						routePath: item.routePath,
						isClose: item.isClose,
						url: item.url,
						isExternal: item.isExternal
					},
				});
			}
		}
		if (item.children && item.children.length > 0) {
			let children = addDynamicRoutes(item.children);
			children.forEach(item => {
				routes.push(item);
			});
		}
	});
	return routes;
}

function clearAllStore() {
	clearAll();
	store.state.global.menus = [];
	store.state.global.tabs = [];
	store.state.global.roles = [];
	store.state.global.allMenus = [];
	store.state.global.navs = [];
	store.state.global.searchRouter = [];
	store.state.global.tabIndex = '0';
	store.state.global.uploadNum = 0;
	store.state.global.uploadFileIndex = 0;
}

export default router;