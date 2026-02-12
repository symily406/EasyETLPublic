package com.easy.system.file.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.vo.FileConfigVo;

/**
 * 文件上传配置-接口
 * @author
 * @time
 */
@Mapper
public interface FileConfigMapper extends MapperDao<FileConfig> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<FileConfigVo> queryFileConfigPage(IPage<FileConfigVo> page,@Param("param")FileConfigDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FileConfigVo> queryFileConfigAll(@Param("param")FileConfigDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	FileConfigVo queryFileConfigByCondition(@Param("param")FileConfigDTO  param);
	//=============代码生成器生成代码结束==============
}
