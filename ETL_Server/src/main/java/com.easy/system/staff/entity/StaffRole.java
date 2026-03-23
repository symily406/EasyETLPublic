package com.easy.system.staff.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 角色用户关联-实体类
 *
 * @table TR_STAFF_ROLE
 * @author
 * @time
 */
@TableName(value = "TR_STAFF_ROLE", autoResultMap = true)
public class StaffRole extends Model<StaffRole> {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "SR_ID", type = IdType.ASSIGN_ID)
	private String srId;


	@TableField(value = "STAFF_ID")
	private String staffId;

	@TableField(value = "ROLE_ID")
	private String roleId;

	public StaffRole() {
		super();
	}


	public String getSrId() {
		return srId;
	}


	public void setSrId(String srId) {
		this.srId = srId;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
