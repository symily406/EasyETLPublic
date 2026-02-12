package com.easy.system.operationLog.dto;

import java.util.List;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.operationLog.entity.OperationLog;

/**
*  操作日志-数据传输对象实体类
 * @author
 * @time
 */
public class OperationLogDTO extends OperationLog{

	/**
	 *
	 */
	private static final long serialVersionUID = -2218951503272163901L;

	/*** 归属企业 */
	private String companyId;

	/**
	 * 归属机构
	 */
	private String orgId;

	/**
	 * 添加用户
	 */
	private String addUserId;

	private List<Integer> logTypes;

	private String staffName;

	private String startDate;

	private String endDate;

	public String getCompanyId() {
		if (StrKit.notBlank(companyId)) {
			return companyId;
		}
		if (getUserLeval() != 100) {
			return getUserCompanyId();
		}
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrgId() {
		if (StrKit.notBlank(orgId)) {
			return orgId;
		}
		if (getUserLeval() != 100&&getUserDutyLeval()==4) {
			return getUserOrgId();
		}
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAddUserId() {
		if (StrKit.notBlank(addUserId)) {
			return addUserId;
		}
		if (getUserDutyLeval() == 0) {
			return getUserId();
		}
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getLogTypes() {
		return logTypes;
	}

	public void setLogTypes(List<Integer> logTypes) {
		this.logTypes = logTypes;
	}



}
