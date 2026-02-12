//================清空所有本地存储==============
export function clearAll() {
	const storeItem = ["headerMenus", "menus", "menuData", "permissions", "menuId", "menuIndex", "token", "tabs",
		"refreshToken", "fileAccept", "name", "leval", "routePath", "isExternal", "url", "platformName","indexPage",
		"runTaskJob"
	];
	storeItem.forEach(item => {
		sessionStorage.removeItem(item);
	});
}

//=============headerMenus关==============
const headerMenus = 'headerMenus';
export function getHeaderMenus() {
	let data = sessionStorage.getItem(headerMenus);
	if (data) {
		return JSON.parse(data);
	}
	return [];
}

export function setHeaderMenus(data) {
	sessionStorage.setItem(headerMenus, JSON.stringify(data));
}

//=================menu相关========================
const menus = 'menus';
export function getMenus() {
	let data = sessionStorage.getItem(menus);
	if (data) {
		return JSON.parse(data);
	}
	return undefined;
}

export function setMenus(data) {
	sessionStorage.setItem(menus, JSON.stringify(data));
}

const menuData = "menuData";
export function getMenuData() {
	let data = sessionStorage.getItem(menuData);
	if (data) {
		return JSON.parse(data);
	}
	return [];
}

export function setMenuData(data) {
	sessionStorage.setItem(menuData, JSON.stringify(data));
}
//===============权限============================
const permissions = "permissions";
export function getPermissions() {
	let data = sessionStorage.getItem(permissions);
	if (data) {
		return JSON.parse(data);
	}
	return [];
}

export function setPermissions(data) {
	sessionStorage.setItem(permissions, JSON.stringify(data));
}

//=================当前菜单id========================
const menuId = "menuId";
export function getMenuId() {
	let data = sessionStorage.getItem(menuId);
	if (data) {
		return data;
	}
	return 0;
}

export function setMenuId(data) {
	sessionStorage.setItem(menuId, data);
}

//=================当前菜单index========================
const menuIndex = "menuIndex";
export function getMenuIndex() {
	let data = sessionStorage.getItem(menuIndex);
	if (data) {
		return data;
	}
	return "0";
}

export function setMenuIndex(data) {
	sessionStorage.setItem(menuIndex, data.toString());
}
//=================token=================
const token = "token";
export function getToken() {
	let data = sessionStorage.getItem(token);
	if (data) {
		return data;
	}
	return undefined;
}

export function setToken(data) {
	sessionStorage.setItem(token, data);
}

export function removeToken(data) {
	sessionStorage.removeItem(token);
}

//=================tabs=================
const tabs = "tabs";
export function geTabs() {
	let data = sessionStorage.getItem(tabs);
	if (data) {
		return data;
	}
	return undefined;
}

export function setTabs(data) {
	sessionStorage.setItem(tabs, data);
}
//================refreshToken============
const refreshToken = "refreshToken";
export function getRefreshToken() {
	let data = sessionStorage.getItem(refreshToken);
	if (data) {
		return data;
	}
	return undefined;
}

export function setRefreshToken(data) {
	sessionStorage.setItem(refreshToken, data);
}

export function removeRefreshToken(data) {
	sessionStorage.removeItem(refreshToken);
}

//================fileAccept============
const fileAccept = "fileAccept";

export function geFileAccept() {
	let data = sessionStorage.getItem(fileAccept);
	if (data) {
		return data;
	}
	return ".jpg,.gif,.png";
}

export function setFileAccept(data) {
	sessionStorage.setItem(fileAccept, data);
}

export function removeFileAccept(data) {
	sessionStorage.removeItem(fileAccept);
}

//================name==========
const name = "name";
export function getName() {
	let data = sessionStorage.getItem(name);
	if (data) {
		return data;
	}
	return undefined;
}

export function setName(data) {
	sessionStorage.setItem(name, data);
}

//================leval==========
const leval = "leval";
export function getLeval() {
	let data = sessionStorage.getItem(leval);
	if (data) {
		return parseInt(data);
	}
	return 0;
}

export function setLeval(data) {
	sessionStorage.setItem(leval, data);
}

//================routePath==========
const routePath = "routePath";
export function getRoutePath() {
	let data = sessionStorage.getItem(routePath);
	if (data) {
		return data;
	}
	return undefined;
}

export function setRoutePath(data) {
	sessionStorage.setItem(routePath, data);
}

//================isExternal==========
const isExternal = "isExternal";
export function getIsExternal() {
	let data = sessionStorage.getItem(isExternal);
	if (data) {
		return data;
	}
	return -1;
}

export function setIsExternal(data) {
	sessionStorage.setItem(isExternal, data);
}

//================url==========
const url = "url";
export function getUrl() {
	let data = sessionStorage.getItem(url);
	if (data) {
		return data;
	}
	return undefined;
}

export function setUrl(data) {
	sessionStorage.setItem(url, data);
}

//================platformName==========
const platformName = "platformName";
export function getPlatformName() {
	let data = sessionStorage.getItem(platformName);
	if (data) {
		return data;
	}
	return undefined;
}

export function setPlatformName(data) {
	sessionStorage.setItem(platformName, data);
}

//================indexPage==========
const indexPage = "indexPage";
export function getIndexPage() {
	let data = sessionStorage.getItem(indexPage);
	if (data) {
		return data;
	}
	return undefined;
}

export function setIndexPage(data) {
	sessionStorage.setItem(indexPage, data);
}

//================runTaskJob==========
const runTaskJob = "runTaskJob";
export function getRunTaskJob() {
	let data = sessionStorage.getItem(runTaskJob);
	if (data) {
		return data;
	}
	return undefined;
}

export function setRunTaskJob(data) {
	sessionStorage.setItem(runTaskJob, data);
}
//=================以下是dataxTask相关================
export function setTaskData(key,data) {
	return sessionStorage.setItem(key,data);
}
export function getTaskData(key) {
	return sessionStorage.getItem(key);
}

export function removeTaskData(key) {
	return sessionStorage.removeItem(key);
}