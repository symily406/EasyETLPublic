package com.easy.system.staff.vo;

import java.util.List;

/**
 * 个人中心
 * @author plink002
 */
public class PersonalCenter {

	/**
	 *
	 */
	private static final long serialVersionUID = -3024652522261630832L;

	private StaffVo staffVo;

	/**
	 * 岗位
	 */
	private List<StaffDutyVo> staffDutyVos;

	/**
	 * 角色
	 */
	private List<StaffRoleVo> staffRoleVos;

	public List<StaffDutyVo> getStaffDutyVos() {
		return staffDutyVos;
	}

	public void setStaffDutyVos(List<StaffDutyVo> staffDutyVos) {
		this.staffDutyVos = staffDutyVos;
	}

	public List<StaffRoleVo> getStaffRoleVos() {
		return staffRoleVos;
	}

	public void setStaffRoleVos(List<StaffRoleVo> staffRoleVos) {
		this.staffRoleVos = staffRoleVos;
	}

	public StaffVo getStaffVo() {
		return staffVo;
	}

	public void setStaffVo(StaffVo staffVo) {
		this.staffVo = staffVo;
	}

}
