import ajax from "@/utils/request.js"
import app from "@/utils/app.js"
export default {
	namespaced: true,
	actions: {
		initProjectTree({ commit }, params) {
			return ajax.get(app.global.etlPath+"/admin/etl/projectTask/queryProjectTree",params);
		}
	},
	mutations: {

	}
}