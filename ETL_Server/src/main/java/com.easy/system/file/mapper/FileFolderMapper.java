package com.easy.system.file.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.file.dto.FileFolderDTO;
import com.easy.system.file.entity.FileFolder;
import com.easy.system.file.vo.FileFolderVo;

/**
 * 文件夹-接口
 * @author
 * @time
 */
@Mapper
public interface FileFolderMapper extends MapperDao<FileFolder> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<FileFolderVo> queryFileFolderPage(IPage<FileFolderVo> page,@Param("param")FileFolderDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FileFolderVo> queryFileFolderAll(@Param("param")FileFolderDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	FileFolderVo queryFileFolderByCondition(@Param("param")FileFolderDTO  param);
	//=============代码生成器生成代码结束==============
}
