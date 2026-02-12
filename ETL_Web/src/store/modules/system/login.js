import ajax from "@/utils/request.js"
import app from "@/utils/app.js"
import store from "@/store/index.js";
import router, { resetRouter } from '@/router';
import { Message } from 'element-ui';
import {setToken,setRefreshToken,setName,setLeval,setIndexPage} from "@/utils/localStore.js";
export default {
	namespaced: true,
	actions: {
		login({ commit }, userInfo) {
			store.state.global.offline=true;
			const { userName, password, captchaVerification } = userInfo;
			return ajax.post(app.global.systemPath + "/admin/system/login", {
				userName: userName.trim(),
				passWord: password.trim(),
				captchaVerification: captchaVerification.trim()
			});
		},
		refreshToken({ commit },otherParams) {
			return ajax.refreshToken(otherParams).then(res => {
				let {token,refreshToken,fileAccept,name,leval,indexPage,platformName}  = res;
				setToken(token);
				setRefreshToken(refreshToken);
				setName(name);
				setLeval(leval);
				if(indexPage){
					setIndexPage(indexPage);
				}
			}).catch(res => {
				Message({
					message: res.message,
					type: 'error',
					duration: 5 * 1000
				});
				router.push({ "path": "/login" });
			}).finally(() => {
				
			});
		},
		loginOut({ commit }) {
			return ajax.get(app.global.systemPath + "/admin/system/loginOut", {});
		}
	}
}
