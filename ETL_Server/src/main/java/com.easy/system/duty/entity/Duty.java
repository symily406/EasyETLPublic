package com.easy.system.duty.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 岗位管理-实体类
 *
 * @table TD_DUTY
 * @author
 * @time
 */
@TableName(value = "TD_DUTY", autoResultMap = true)
public class Duty extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "DUTY_ID", type = IdType.ASSIGN_ID)
	private String dutyId;


	@TableField(value = "DUTY_NAME")
	private String dutyName;


	@TableField(value = "DUTY_LEVAL")
	private Integer dutyLeval;

	public Duty() {
		super();
	}


	public String getDutyId() {
		return dutyId;
	}


	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}


	public String getDutyName() {
		return dutyName;
	}


	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public Integer getDutyLeval() {
		return dutyLeval;
	}

	public void setDutyLeval(Integer dutyLeval) {
		this.dutyLeval = dutyLeval;
	}
}
