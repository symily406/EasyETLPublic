package com.easy.system.dict.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 字典主表-实体类
 *
 * @table TD_DICT
 * @author
 * @time
 */
@TableName(value = "TD_DICT", autoResultMap = true)
public class Dict extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "DICT_ID", type = IdType.ASSIGN_ID)
	private String dictId;

	/**
	 * 编码
	 */
	@TableField(value = "DICT_CODE")
	@Length(max = 32, message = "dictCode编码不能超过32个字符")
	private String dictCode;
	/**
	 * 字典名称
	 */
	@TableField(value = "DICT_NAME")
	@NotEmpty(message = "dictName字典名称不能为空")
	@Length(max = 128, message = "dictName字典名称不能超过128个字符")
	private String dictName;


	public Dict() {
		super();
	}

	/**
	 * 主键-数据库字段:DICT_ID
	 */
	public String getDictId() {
		return dictId;
	}

	/**
	 * 主键-数据库字段:DICT_ID
	 */
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	/**
	 * 编码-数据库字段:DICT_CODE
	 */
	public String getDictCode() {
		return dictCode;
	}

	/**
	 * 编码-数据库字段:DICT_CODE
	 */
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	/**
	 * 字典名称-数据库字段:DICT_NAME
	 */
	public String getDictName() {
		return dictName;
	}

	/**
	 * 字典名称-数据库字段:DICT_NAME
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

}
