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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;


	@TableField(value = "STAFF_ID")
	private String staffId;

	@TableField(value = "DUTY_ID")
	private String dutyId;

	public StaffDuty() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getDutyId() {
		return dutyId;
	}


	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

}
