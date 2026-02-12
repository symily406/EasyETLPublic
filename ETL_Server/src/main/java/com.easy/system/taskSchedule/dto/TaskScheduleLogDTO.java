package com.easy.system.taskSchedule.dto;


import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.spring.boot.ext.kit.StrKit;

/**
*  任务调度日志-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class TaskScheduleLogDTO extends TaskScheduleLog {

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
}
