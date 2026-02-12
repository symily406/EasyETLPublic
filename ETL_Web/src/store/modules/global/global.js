import variables from '@/assets/scss/variables.scss';

export default {
	namespaced: true,
	state: {
		tokenName: 'Access-Token',
		sideBarWidth: variables.sideBarWidth,
		iframeMask:true,
		headerHeight:variables.headerHeight,
		contentHeight:window.innerHeight - variables.headerHeight,
		menuBackgroundColor:variables.menuBg,
		menuTextColor:variables.menuText,
		menuActiveTextColor:variables.menuActiveText,
		isCollapse: false,
		tabs: [],
		roles: [],
		menus: [],
		allMenus: [],
		navs: [],
		searchRouter: [],
		tabIndex: '0',
		tableWidth: 100,
		dialogTop: '1vh',
		uploadNum: 0,
		uploadFileIndex: 0,
		offline:true,
		headerMenus:[],
		taskMenus:[],
		taskMenuShow:false,
		taskTabName:"taskTab",
		taskTableIndexName:"taskTableIndex",
		defaultTaskId:"12345678901234567890",
		flowStartNodeType:0,
		flowDataxNodeType:1,
		flowSqlNodeType:2,
		flowEndNodeType:99999
	},
	actions: {

	},
	getters: {
		getTab: (state) => (key) => {
			return state.tabs.filter(item => item.id == key)[0];
		}
	},
	mutations: {
		setIsCollapse: (state, isCollapse) => {
			state.isCollapse = isCollapse;
			state.sideBarWidth = variables.sideBarWidth;
			if (isCollapse) {
				state.sideBarWidth = variables.sideBarMinWidth;
			}
			state.tableWidth = window.innerWidth - state.sideBarWidth - 10;
		},
		setTaskMenuShow: (state, taskMenuShow) => {
			state.taskMenuShow = taskMenuShow;
		},
		setTaskMenu: (state, taskMenus) => {
			state.taskMenus = taskMenus;
		},
		setTableWidth: (state) => {
			state.tableWidth = window.innerWidth - state.sideBarWidth - 10;
		},
		updateTabs: (state, tabs) => {
			state.tabs = tabs;
		},
		updateActiveTab: (state, tabIndex) => {
			state.tabIndex = tabIndex;
		},
		uploadFile(state, index) {
			state.uploadFileIndex = index;
		},
		uploadNum(state, num) {
			state.uploadNum = num;
		},
		setPlatformName(state, platformName) {
			state.platformName = platformName;
		}
	}
}
