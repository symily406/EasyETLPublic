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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "DICT_ID", type = IdType.ASSIGN_ID)
	private String dictId;


	@TableField(value = "DICT_CODE")
	private String dictCode;

	@TableField(value = "DICT_NAME")
	private String dictName;


	public Dict() {
		super();
	}


	public String getDictId() {
		return dictId;
	}


	public void setDictId(String dictId) {
		this.dictId = dictId;
	}


	public String getDictCode() {
		return dictCode;
	}


	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}


	public String getDictName() {
		return dictName;
	}


	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

}
