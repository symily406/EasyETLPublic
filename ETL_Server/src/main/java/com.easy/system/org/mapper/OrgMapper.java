package com.easy.system.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.org.entity.Org;
import com.easy.system.org.vo.OrgDropDownVo;
import com.easy.system.org.vo.OrgVo;

/**
 * 机构-接口
 *
 * @author
 * @time
 */
@Mapper
public interface OrgMapper extends MapperDao<Org> {



	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<OrgVo> queryOrgPage(IPage<OrgVo> page, @Param("param") OrgDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<OrgVo> queryOrgAll(@Param("param") OrgDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	OrgVo queryOrgByCondition(@Param("param") OrgDTO param);
	// =============代码生成器生成代码结束==============
}
