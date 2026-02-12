package com.easy.system.area.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.area.dto.AreaDTO;
import com.easy.system.area.entity.Area;
import com.easy.system.area.vo.AreaVo;

/**
 * 地区表-接口
 *
 * @author
 * @time
 */
@Mapper
public interface AreaMapper extends MapperDao<Area> {


	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<AreaVo> queryAreaAll(@Param("param") AreaDTO param);

}
