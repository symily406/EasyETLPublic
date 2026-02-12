package com.easy.system.role.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 角色功能关联表-实体类
 *
 * @table TR_ROLE_FUNCTION
 * @author
 * @time
 */
@TableName(value = "TR_ROLE_FUNCTION", autoResultMap = true)
public class RoleFunction extends Model<RoleFunction> {

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
	 * 功能主键
	 */
	@TableField(value = "R_ID")
	@NotEmpty(message = "rId权限主键不能为空")
	@Length(max = 32, message = "rId权限主键不能超过32个字符")
	private String rId;

	/**
	 * 功能主键
	 */
	@TableField(value = "F_ID")
	@NotEmpty(message = "fId功能主键不能为空")
	@Length(max = 32, message = "fId功能主键不能超过32个字符")
	private String fId;
	/**
	 * 层级
	 */
	@TableField(value = "LEVAL")
	private Integer leval;

	public RoleFunction() {
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
	 * 层级-数据库字段:LEVAL
	 */
	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getfId() {
		return fId;
	}

	public void setfId(String fId) {
		this.fId = fId;
	}



}
