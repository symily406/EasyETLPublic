import moment from 'moment';
//全局参数
const global = {
	"systemPath": "/system",
	"etlPath":"/system"
}



/**
 * 设置分页参数
 * @param {Object} data
 */
function setPage(data) {
	const totalPage = data.pages;
	const pageNumber = data.current;
	const pageSize = data.size;
	const total = data.total;
	return { totalPage: totalPage, pageNumber: pageNumber, pageSize: pageSize,total}
}

/**
 * 合并对象
 * @param {Object} target
 * @param {Object} source
 */
function extend(target, source) {
	for (var obj in source) {
		target[obj] = source[obj];
	}
	return target;
}

/**
 * 初始化表单数据
 */
function initFormData(form, row) {
	for (let col in form) {
		if (row[col]) {
			form[col] = row[col];
		}
	}
	return form;
}

/**
 * 表格树展开关闭
 * @param obj 表格树对像
 * @param list treeData 数据
 * @param boolen isExpand 展开(true)/关闭(false)
 */
function tableTreeExpand(obj, treeData, isExpand) {
	for (let i = 0; i < treeData.length; i++) {
		let item = treeData[i];
		obj.toggleRowExpansion(item, isExpand);
		if (item.children) {
			tableTreeExpand(item.children, isExpand);
		}
	}
}

function fileSize(size) {
	if (size && size > 0) {
		let kb = 1024
		let unit = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
		let i = Math.floor(Math.log(size) / Math.log(kb))
		return (size / Math.pow(kb, i)).toPrecision(3) + ' ' + unit[i];
	}
	return "";
}

/**
 * 添加分页参数
 * @param {Object} queryParam
 * @param {Object} page
 */
function addPageParam(queryParam, page) {
	queryParam['current'] = page.pageNumber;
	queryParam['size'] = page.pageSize;
	return queryParam;
}

/**
 * 生成uuid
 */
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid;
}

function parameterParse(str) {  
    const regex = /\$\{([^}]+)\}/g;  
    const variables = [];  
    let matchArray;  
    while ((matchArray = regex.exec(str)) !== null) {  
        variables.push(matchArray[1]);  
    }  
    if (matchArray === null && variables.length === 0) {  
        return [];  
    }  
    return variables;  
}

function msToTime(duration){
	var time = moment.duration(duration, 'milliseconds');
	return time.hours() + "小时" + time.minutes() + "分钟" + time.seconds() + "秒";
}


export default {
	global,
	setPage,
	extend,
	initFormData,
	tableTreeExpand,
	fileSize,
	addPageParam,
	uuid,
	parameterParse,
	msToTime
}
