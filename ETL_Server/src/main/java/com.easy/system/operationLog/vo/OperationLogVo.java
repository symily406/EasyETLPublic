package com.easy.system.operationLog.vo;

import com.easy.system.operationLog.entity.OperationLog;

/**
*  操作日志-视图实体类
 * @author
 * @time
 */
public class OperationLogVo extends OperationLog {

	/**
	 *
	 */
	private static final long serialVersionUID = -4983516740910970377L;


	private String staffName;

	private String requestInfo;

	private String responseInfo;


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
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
