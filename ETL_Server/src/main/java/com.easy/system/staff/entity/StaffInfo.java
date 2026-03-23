package com.easy.system.staff.entity;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 用户信息-实体类
 *
 * @table TD_STAFF_INFO
 * @author
 * @time
 */
@TableName(value = "TD_STAFF_INFO", autoResultMap = true)
public class StaffInfo extends Model<StaffInfo> implements Serializable {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "STAFF_ID", type = IdType.ASSIGN_ID)
	private String staffId;


	@TableField(value = "NAME")
	private String name;

	@TableField(value = "HEAD_PORTRAIT")
	private String headPortrait;

	@TableField(value = "PHONE")
	private String phone;


	@TableField(value = "SEX")
	private int sex;

	public StaffInfo() {
		super();
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getHeadPortrait() {
		return headPortrait;
	}


	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
