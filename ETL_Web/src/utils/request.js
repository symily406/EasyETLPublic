import axios from "axios"
import qs from "qs"
import store from "@/store/index";
import router from "@/router";
import app from "@/utils/app";
import {
	Message,
	MessageBox
} from 'element-ui';
import {
	getToken,
	setToken,
	removeToken,
	getRefreshToken,
	setRefreshToken,
	removeRefreshToken,
	setFileAccept,
	removeFileAccept
} from "@/utils/localStore.js";
axios.defaults.timeout = 120000 //响应时间
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'; //配置请求头
axios.defaults.baseURL = process.env.VUE_APP_BASE_API; //配置接口地址


// 是否正在刷新的标记
let isRefreshing = false;
// 重试队列，每一项将是一个待执行的函数形式
let requests = [];
let offlineMessage = false;

// 创建一个axios实例
const instance = axios.create();

instance.interceptors.request.use((config) => {
		let token = getToken();
		if (token) {
			config.headers[store.state.global.tokenName] = token;
		}
		return config
	},
	error => {
		console.log(error) // for debug
		return Promise.reject(error)
	}
);


instance.interceptors.response.use(
	response => {
		// console.log(instance);
		const res = response.data
		if (res.code !== 20000) {
			if (res.code === 50008 || res.code === 50014) {
				const config = response.config;
				if (!isRefreshing) {
					isRefreshing = true;
					return refreshToken(null).then(res => {
						const {
							token,
							refreshToken,
							fileAccept
						} = res;
						instance.setToken(token, refreshToken, fileAccept);
						config.headers[store.state.global.tokenName] = token;
						// 已经刷新了token，将所有队列中的请求进行重试
						requests.forEach(cb => cb(token))
						requests = []
						return instance(config);
					}).catch(res => {
						Message({
							message: res.message,
							type: 'error',
							duration: 5 * 1000
						});
						loginOut();
					}).finally(() => {
						isRefreshing = false
					});
				} else {
					return new Promise((resolve) => {
						requests.push((token) => {
							config.headers[store.state.global.tokenName] = token
							resolve(instance(config))
						});
					});
				}
			} else if (res.code == 70001) {
				if (store.state.global.offline) {
					store.state.global.offline = false;
					MessageBox.alert(res.message, '警告', {
						confirmButtonText: '确定',
						type: 'warning',
						callback: action => {
							loginOut();
						}
					});
				}
			} else {
				Message({
					showClose: true,
					dangerouslyUseHTMLString: true,
					message: "<div style=\"max-height: 400px; overflow: auto;min-height:20px\">" + res
						.message + "</div>",
					type: 'error',
					duration: 5 * 1000
				});
			}
			return Promise.reject(new Error(res.message || 'Error'))
		} else {
			return res
		}
	},
	error => {
		// console.log('err' + error) // for debug
		Message({
			message: error.message,
			type: 'error',
			duration: 5 * 1000
		})
		return Promise.reject(error)
	}
)

// 给实例添加一个setToken方法，用于登录后将最新token动态添加到header，同时将token保存在localStorage中
instance.setToken = (token, refreshToken, fileAccept) => {
	instance.defaults.headers[store.state.global.tokenName] = token;
	setToken(token);
	setRefreshToken(refreshToken);
	setFileAccept(fileAccept);
}

//刷新token
function refreshToken(otherParams) {
	let params = {
		"token": getRefreshToken()
	};
	if (otherParams) {
		for (var obj in otherParams) {
			params[obj] = otherParams[obj];
		}
	}
	return instance.post(app.global.systemPath + '/admin/system/refreshToken', qs.stringify(params)).then(res => res
		.data);
}
//退出登陆
function loginOut() {
	store.state.global.offline = true;
	store.dispatch('system/login/loginOut').then(res => {
		removeToken();
		removeRefreshToken();
		removeFileAccept();
		router.push({
			"path": "/login"
		});
	}).catch(error => {

	});
}

//返回一个Promise(发送post请求)
function post(url, params) {
	return new Promise((resolve, reject) => {
		instance.post(url, qs.stringify(params))
			.then(response => {
				resolve(response);
			}, err => {
				reject(err);
			})
			.catch((error) => {
				reject(error)
			})
	})
}

//文件上传
function upload(url, params) {
	return new Promise((resolve, reject) => {
		instance.post(url, params, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			})
			.then(response => {
				resolve(response);
			}, err => {
				reject(err);
			})
			.catch((error) => {
				reject(error)
			})
	})
}

////返回一个Promise(发送get请求)
function get(url, param) {
	return new Promise((resolve, reject) => {
		instance.get(url, {
				params: param
			})
			.then(response => {
				resolve(response)
			}, err => {
				reject(err)
			})
			.catch((error) => {
				reject(error)
			})
	})
}
export default {
	get,
	post,
	upload,
	refreshToken
}