package com.easy.system.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.staff.dto.StaffInfoDTO;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.vo.StaffInfoVo;

/**
 * 用户信息-接口
 * @author
 * @time
 */
@Mapper
public interface StaffInfoMapper extends MapperDao<StaffInfo> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<StaffInfoVo> queryStaffInfoPage(IPage<StaffInfoVo> page,@Param("param")StaffInfoDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<StaffInfoVo> queryStaffInfoAll(@Param("param")StaffInfoDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	StaffInfoVo queryStaffInfoByCondition(@Param("param")StaffInfoDTO  param);
	//=============代码生成器生成代码结束==============
}
