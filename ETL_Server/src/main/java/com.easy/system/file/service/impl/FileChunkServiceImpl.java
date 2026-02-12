package com.easy.system.file.service.impl;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.boot.ext.kit.PathKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.etl.core.init.Config;
import com.easy.system.file.dto.FileChunkDTO;
import com.easy.system.file.entity.FileChunk;
import com.easy.system.file.mapper.FileChunkMapper;
import com.easy.system.file.service.IFileChunkService;
import com.easy.system.file.vo.FileChunkVo;

import cn.hutool.core.io.FileUtil;

/**
 * 文件上传分片-服务类
 * @author
 * @time
 */
@Service
public class FileChunkServiceImpl extends BaseServiceImpl<FileChunkMapper, FileChunk> implements IFileChunkService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateFileChunk(FileChunk fileChunk) {
		return saveOrUpdate(fileChunk);
	}


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<FileChunkVo> queryFileChunkAll(String identifier) {
		FileChunkDTO param=new FileChunkDTO();
		param.setIdentifier(identifier);
		return baseMapper.queryFileChunkAll(param);
	}

	/**
	 * 删除分片
	 * @param identifier
	 * @return
	 */
	@Override
	public boolean fileRemoved(String identifier) {
		FileChunk fileChunk=this.getOne(new QueryWrapper<FileChunk>().lambda().eq(FileChunk::getIdentifier, identifier),false);
		if (fileChunk!=null) {
			File saveFolder = FileUtil.mkdir(PathKit.handle(Config.chunkPath.concat("/").concat(fileChunk.getIdentifier())));
			FileUtil.del(saveFolder);

			Map<String, Object> param=new HashMap<String, Object>();
			param.put("identifier", identifier);
			return baseMapper.deleteByCondition("DELETE FROM TM_FILE_CHUNK WHERE IDENTIFIER=:identifier", param);
		}
		return false;
	}


	/**
	 * 更新分片parentId
	 * @param identifier
	 * @return
	 */
	@Override
	public boolean updateChunkParentId(String identifier,String parentId) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("identifier", identifier);
		param.put("parentId", parentId);
		return this.updateByCondition("UPDATE TM_FILE_CHUNK SET PARENT_ID=:parentId WHERE IDENTIFIER=:identifier", param);
	}


	/**
	 * 删除分片信息
	 * @param identifier
	 * @return
	 */
	@Override
	public boolean deleteFileChunkById(FileChunk fileChunk) {
		FileUtil.del(FileUtil.file(fileChunk.getSavePath()).getParent());
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("identifier", fileChunk.getIdentifier());
		return this.deleteByCondition("DELETE FROM TM_FILE_CHUNK  WHERE IDENTIFIER=:identifier",param);
	}


}
