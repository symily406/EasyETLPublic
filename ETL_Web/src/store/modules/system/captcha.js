import ajax from "@/utils/request.js"
import app from "@/utils/app.js"
import router, { resetRouter } from '@/router'
export default {
	namespaced: true,
	actions: {
		captcha({ commit },time) {
			return ajax.get(app.global.systemPath+"/captcha/index",time);
		}
	},
	mutations: {

	}
}
