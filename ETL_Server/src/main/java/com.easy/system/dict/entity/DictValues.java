package com.easy.system.dict.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 字典值-实体类
 *
 * @table TD_DICT_VALUES
 * @author
 * @time
 */
@TableName(value = "TD_DICT_VALUES", autoResultMap = true)
public class DictValues extends BaseEntity {

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
	 * 归属字典编码
	 */
	@TableField(value = "DICT_CODE")
	@NotEmpty(message = "dictCode归属字典编码不能为空")
	@Length(max = 32, message = "dictCode归属字典编码不能超过32个字符")
	private String dictCode;
	/**
	 * 编码
	 */
	@TableField(value = "CODE")
	@Length(max = 128, message = "code编码不能超过128个字符")
	private String code;
	/**
	 * 名称
	 */
	@TableField(value = "NAME")
	@Length(max = 64, message = "name名称不能超过64个字符")
	private String name;
	/**
	 * 值
	 */
	@TableField(value = "VALUE")
	@Length(max = 64, message = "value值不能超过64个字符")
	private String value;


	public DictValues() {
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
	 * 归属字典编码-数据库字段:DICT_CODE
	 */
	public String getDictCode() {
		return dictCode;
	}

	/**
	 * 归属字典编码-数据库字段:DICT_CODE
	 */
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	/**
	 * 编码-数据库字段:CODE
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 编码-数据库字段:CODE
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 值-数据库字段:VALUE
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 值-数据库字段:VALUE
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
