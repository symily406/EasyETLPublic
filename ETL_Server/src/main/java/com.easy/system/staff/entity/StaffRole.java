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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "SR_ID", type = IdType.ASSIGN_ID)
	private String srId;

	/**
	 * 用户
	 */
	@TableField(value = "STAFF_ID")
	@NotEmpty(message = "staffId用户不能为空")
	@Length(max = 32, message = "staffId用户不能超过32个字符")
	private String staffId;
	/**
	 * 角色
	 */
	@TableField(value = "ROLE_ID")
	@Length(max = 32, message = "roleId角色不能超过32个字符")
	private String roleId;

	public StaffRole() {
		super();
	}

	/**
	 * 主键-数据库字段:SR_ID
	 */
	public String getSrId() {
		return srId;
	}

	/**
	 * 主键-数据库字段:SR_ID
	 */
	public void setSrId(String srId) {
		this.srId = srId;
	}

	/**
	 * 用户-数据库字段:STAFF_ID
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * 用户-数据库字段:STAFF_ID
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * 角色-数据库字段:ROLE_ID
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 角色-数据库字段:ROLE_ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
