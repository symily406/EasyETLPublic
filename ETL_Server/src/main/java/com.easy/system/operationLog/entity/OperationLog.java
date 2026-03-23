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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
	private String logId;


	@TableField(value = "NAME")
	private String name;

	@TableField(value = "MODE")
	private String mode;


	@TableField(value = "URL")
	private String url;


	@TableField(value = "CONTROLLER")
	private String controller;


	@TableField(value = "METHOD")
	private String method;

	@TableField(value = "IP")
	private String ip;

	@TableField(value = "USAGE_TIME")
	private Long usageTime;

	@TableField(value = "LOG_TYPE")
	private Integer logType;

	@TableField(value = "ENABLE")
	private Integer enable;

	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;

	@TableField(value = "IS_DEL", fill = FieldFill.INSERT)
	private Integer isDel;

	public OperationLog() {
		super();
	}


	public String getLogId() {
		return logId;
	}


	public void setLogId(String logId) {
		this.logId = logId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public Long getUsageTime() {
		return usageTime;
	}

	public void setUsageTime(Long usageTime) {
		this.usageTime = usageTime;
	}


	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}


	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}


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
