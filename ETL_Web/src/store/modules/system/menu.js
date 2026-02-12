import ajax from "@/utils/request.js"
import app from "@/utils/app.js"
export default {
	namespaced: true,
	actions: {
		initMenu({ commit }, userInfo) {
			return ajax.get(app.global.systemPath+"/admin/system/menu/menu/menus",{});
		}
	},
	mutations: {

	}
}
