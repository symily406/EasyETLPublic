package com.easy.system.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.staff.dto.StaffDutyDTO;
import com.easy.system.staff.entity.StaffDuty;
import com.easy.system.staff.vo.StaffDutyVo;

/**
 * 用户岗位-接口
 * @author
 * @time
 */
@Mapper
public interface StaffDutyMapper extends MapperDao<StaffDuty> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<StaffDutyVo> queryStaffDutyPage(IPage<StaffDutyVo> page,@Param("param")StaffDutyDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<StaffDutyVo> queryStaffDutyAll(@Param("param")StaffDutyDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	StaffDutyVo queryStaffDutyByCondition(@Param("param")StaffDutyDTO  param);
	//=============代码生成器生成代码结束==============
}
