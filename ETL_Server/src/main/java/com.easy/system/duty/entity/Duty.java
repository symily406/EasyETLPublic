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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "DUTY_ID", type = IdType.ASSIGN_ID)
	private String dutyId;

	/**
	 * 岗位名称
	 */
	@TableField(value = "DUTY_NAME")
	@NotEmpty(message = "dutyName岗位名称不能为空")
	@Length(max = 64, message = "dutyName岗位名称不能超过64个字符")
	private String dutyName;

	/**
	 * 岗位层级
	 */
	@TableField(value = "DUTY_LEVAL")
	private Integer dutyLeval;

	public Duty() {
		super();
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

	/**
	 * 岗位名称-数据库字段:DUTY_NAME
	 */
	public String getDutyName() {
		return dutyName;
	}

	/**
	 * 岗位名称-数据库字段:DUTY_NAME
	 */
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
