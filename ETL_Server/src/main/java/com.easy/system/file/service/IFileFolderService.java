package com.easy.system.file.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.dto.FileFolderDTO;
import com.easy.system.file.entity.FileFolder;
import com.easy.system.file.vo.FileFolderTreeVo;
import com.easy.system.file.vo.FileFolderVo;

/**
 * 文件夹-服务类
 * @author
 * @time
 */
public interface  IFileFolderService extends IBaseService<FileFolder> {

	/**
	 * 删除文件夹
	 * @param folderId
	 * @return
	 */
	public boolean deleteFolder(String folderId);

	/**
	 * 文件夹树
	 * @param fileConfigDTO
	 * @param fileFolderDTO
	 * @return
	 */
	public List<FileFolderTreeVo> folderTreeVos(FileConfigDTO fileConfigDTO,FileFolderDTO fileFolderDTO);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param fileFolder
	 */
	public boolean saveOrUpdateFileFolder(FileFolder fileFolder);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FileFolderVo> queryFileFolderPage(IPage<FileFolderVo> page,FileFolderDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FileFolderVo> queryFileFolderAll(FileFolderDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public FileFolderVo queryFileFolderByCondition(FileFolderDTO param);
	//================代码生成器生成代码结束=================
}
