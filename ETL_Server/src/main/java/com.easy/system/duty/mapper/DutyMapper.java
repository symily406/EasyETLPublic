package com.easy.system.duty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.duty.dto.DutyDTO;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.vo.DutyVo;

/**
 * 岗位管理-接口
 *
 * @author
 * @time
 */
@Mapper
public interface DutyMapper extends MapperDao<Duty> {

	/**
	 * 查询用户岗位，只返回一个最高级的岗位
	 * @param param
	 * @return
	 */
	List<DutyVo> queryDutyByStaffId(String staffId);

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<DutyVo> queryDutyPage(IPage<DutyVo> page, @Param("param") DutyDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<DutyVo> queryDutyAll(@Param("param") DutyDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	DutyVo queryDutyByCondition(@Param("param") DutyDTO param);
	// =============代码生成器生成代码结束==============
}
