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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "STAFF_ID", type = IdType.ASSIGN_ID)
	private String staffId;

	/**
	 * 姓名
	 */
	@TableField(value = "NAME")
	@NotEmpty(message = "name姓名不能为空")
	@Length(max = 64, message = "name姓名不能超过64个字符")
	private String name;
	/**
	 * 头像
	 */
	@TableField(value = "HEAD_PORTRAIT")
	@Length(max = 128, message = "headPortrait头像不能超过128个字符")
	private String headPortrait;
	/**
	 * 手机号码
	 */
	@TableField(value = "PHONE")
	@Length(max = 20, message = "phone手机号码不能超过20个字符")
	private String phone;

	/**
	 * 手机号
	 */
	@TableField(value = "SEX")
	private int sex;

	public StaffInfo() {
		super();
	}

	/**
	 * 主键-数据库字段:STAFF_ID
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * 主键-数据库字段:STAFF_ID
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * 姓名-数据库字段:NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * 姓名-数据库字段:NAME
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 头像-数据库字段:HEAD_PORTRAIT
	 */
	public String getHeadPortrait() {
		return headPortrait;
	}

	/**
	 * 头像-数据库字段:HEAD_PORTRAIT
	 */
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	/**
	 * 手机号码-数据库字段:PHONE
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 手机号码-数据库字段:PHONE
	 */
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
