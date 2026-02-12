package com.easy.system.operationLog.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.hibernate.validator.constraints.Length;

import com.easy.etl.core.entity.BaseEntity;

/**
 * 操作日志-实体类
 *
 * @table TH_OPERATION_LOG
 * @author
 * @time
 */
@TableName(value = "TH_OPERATION_LOG", autoResultMap = true)
public class OperationLog extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
	private String logId;

	/**
	 * 名称
	 */
	@TableField(value = "NAME")
	@Length(max = 128, message = "name名称不能超过128个字符")
	private String name;
	/**
	 * 请求方式
	 */
	@TableField(value = "MODE")
	@Length(max = 16, message = "mode请求方式不能超过16个字符")
	private String mode;

	/**
	 * 请求地址
	 */
	@TableField(value = "URL")
	@Length(max = 512, message = "url请求地址不能超过512个字符")
	private String url;

	/**
	 *请求Controller
	 */
	@TableField(value = "CONTROLLER")
	@Length(max = 128, message = "请求Controller不能超过128个字符")
	private String controller;

	/**
	 *方法
	 */
	@TableField(value = "METHOD")
	@Length(max = 16, message = "方法不能超过16个字符")
	private String method;
	/**
	 * IP地址
	 */
	@TableField(value = "IP")
	@Length(max = 16, message = "IP地址不能超过16个字符")
	private String ip;
	/**
	 * 用时
	 */
	@TableField(value = "USAGE_TIME")
	private Long usageTime;
	/**
	 * 日志类型
	 */
	@TableField(value = "LOG_TYPE")
	private Integer logType;
	/**
	 * 启用
	 */
	@TableField(value = "ENABLE")
	private Integer enable;
	/**
	 * 显示排序
	 */
	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;
	/**
	 * 删除标识0:已删,1:正常
	 */
	@TableField(value = "IS_DEL", fill = FieldFill.INSERT)
	private Integer isDel;

	public OperationLog() {
		super();
	}

	/**
	 * 主键-数据库字段:LOG_ID
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * 主键-数据库字段:LOG_ID
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 请求方式-数据库字段:MODE
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * 请求方式-数据库字段:MODE
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * IP地址-数据库字段:IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * IP地址-数据库字段:IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 用时-数据库字段:USAGE_TIME
	 */
	public Long getUsageTime() {
		return usageTime;
	}

	public void setUsageTime(Long usageTime) {
		this.usageTime = usageTime;
	}

	/**
	 * 日志类型-数据库字段:LOGIN_TYPE
	 */
	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	/**
	 * 启用-数据库字段:ENABLE
	 */
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * 显示排序-数据库字段:SHOW_ORDER
	 */
	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public Integer getIsDel() {
		return isDel;
	}

	@Override
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
