package com.easy.system.staff.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 用户岗位-实体类
 *
 * @table TD_STAFF_DUTY
 * @author
 * @time
 */
@TableName(value = "TD_STAFF_DUTY", autoResultMap = true)
public class StaffDuty extends Model<StaffDuty> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 用户主键
	 */
	@TableField(value = "STAFF_ID")
	@NotEmpty(message = "staffId用户主键不能为空")
	@Length(max = 32, message = "staffId用户主键不能超过32个字符")
	private String staffId;
	/**
	 * 岗位ID
	 */
	@TableField(value = "DUTY_ID")
	@NotEmpty(message = "dutyId岗位ID不能为空")
	@Length(max = 32, message = "dutyId岗位ID不能超过32个字符")
	private String dutyId;

	public StaffDuty() {
		super();
	}

	/**
	 * 主键-数据库字段:ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键-数据库字段:ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 用户主键-数据库字段:STAFF_ID
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * 用户主键-数据库字段:STAFF_ID
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * 岗位ID-数据库字段:DUTY_ID
	 */
	public String getDutyId() {
		return dutyId;
	}

	/**
	 * 岗位ID-数据库字段:DUTY_ID
	 */
	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

}
