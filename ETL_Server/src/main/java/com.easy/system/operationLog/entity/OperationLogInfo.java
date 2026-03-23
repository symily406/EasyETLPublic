package com.easy.system.operationLog.entity;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 操作日志详情-实体类
 *
 * @table TH_OPERATION_LOG_INFO
 * @author
 * @time
 */
@TableName(value = "TH_OPERATION_LOG_INFO", autoResultMap = true)
public class OperationLogInfo extends Model<OperationLogInfo> {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
	private String logId;


	@TableField(value = "REQUEST_INFO")
	private String requestInfo;

	@TableField(value = "RESPONSE_INFO")
	private String responseInfo;

	public OperationLogInfo() {
		super();
	}


	public String getLogId() {
		return logId;
	}


	public void setLogId(String logId) {
		this.logId = logId;
	}


	public String getRequestInfo() {
		return requestInfo;
	}


	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}


	public String getResponseInfo() {
		return responseInfo;
	}


	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

}
