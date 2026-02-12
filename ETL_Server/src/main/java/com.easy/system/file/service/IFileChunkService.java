package com.easy.system.file.service;


import java.util.List;

import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.file.entity.FileChunk;
import com.easy.system.file.vo.FileChunkVo;

/**
 * 文件上传分片-服务类
 * @author
 * @time
 */
public interface  IFileChunkService extends IBaseService<FileChunk> {

	/**
	 * 删除分片信息
	 * @param identifier
	 * @return
	 */
	public boolean deleteFileChunkById(FileChunk fileChunk);

	/**
	 * 更新分片parentId
	 * @param identifier
	 * @return
	 */
	public boolean updateChunkParentId(String identifier,String parentId);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param fileChunk
	 */
	public boolean saveOrUpdateFileChunk(FileChunk fileChunk);


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FileChunkVo> queryFileChunkAll(String identifier);

	/**
	 * 删除分片
	 * @param identifier
	 * @return
	 */
	boolean fileRemoved(String identifier);

	//================代码生成器生成代码结束=================
}
