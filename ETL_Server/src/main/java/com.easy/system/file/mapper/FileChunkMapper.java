package com.easy.system.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.file.dto.FileChunkDTO;
import com.easy.system.file.entity.FileChunk;
import com.easy.system.file.vo.FileChunkVo;

/**
 * 文件上传分片-接口
 *
 * @author
 * @time
 */
@Mapper
public interface FileChunkMapper extends MapperDao<FileChunk> {

	// =============代码生成器生成代码开始==============
	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<FileChunkVo> queryFileChunkAll(@Param("param") FileChunkDTO param);
	// =============代码生成器生成代码结束==============
}
