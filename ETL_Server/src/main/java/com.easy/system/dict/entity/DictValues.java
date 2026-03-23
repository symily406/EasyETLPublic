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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;


	@TableField(value = "DICT_CODE")
	private String dictCode;

	@TableField(value = "CODE")
	private String code;

	@TableField(value = "NAME")
	private String name;

	@TableField(value = "VALUE")
	private String value;


	public DictValues() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDictCode() {
		return dictCode;
	}


	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

}
