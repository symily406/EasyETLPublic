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
	 * 请求信息
	 */
	@TableField(value = "REQUEST_INFO")
	@Length(max = 500, message = "requestInfo请求信息不能超过500个字符")
	private String requestInfo;
	/**
	 * 向应信息
	 */
	@TableField(value = "RESPONSE_INFO")
	@Length(max = 500, message = "responseInfo向应信息不能超过500个字符")
	private String responseInfo;

	public OperationLogInfo() {
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
	 * 请求信息-数据库字段:REQUEST_INFO
	 */
	public String getRequestInfo() {
		return requestInfo;
	}

	/**
	 * 请求信息-数据库字段:REQUEST_INFO
	 */
	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}

	/**
	 * 向应信息-数据库字段:RESPONSE_INFO
	 */
	public String getResponseInfo() {
		return responseInfo;
	}

	/**
	 * 向应信息-数据库字段:RESPONSE_INFO
	 */
	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

}
