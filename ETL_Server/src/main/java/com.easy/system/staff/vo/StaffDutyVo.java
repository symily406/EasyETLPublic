package com.easy.system.staff.vo;

import com.easy.system.staff.entity.StaffDuty;

/**
*  用户岗位-视图实体类
 * @author
 * @time
 */
public class StaffDutyVo extends StaffDuty {

	/**
	 *
	 */
	private static final long serialVersionUID = 827036658684005798L;

	private String dutyName;

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

}
