package com.easy.system.file.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.file.dto.FileInfoDTO;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.vo.FileInfoVo;

/**
 * 附件-接口
 * @author
 * @time
 */
@Mapper
public interface FileInfoMapper extends MapperDao<FileInfo> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<FileInfoVo> queryFileInfoPage(IPage<FileInfoVo> page,@Param("param")FileInfoDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FileInfoVo> queryFileInfoAll(@Param("param")FileInfoDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	FileInfoVo queryFileInfoByCondition(@Param("param")FileInfoDTO  param);
	//=============代码生成器生成代码结束==============
}
