package com.easy.system.file.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.file.dto.FileInfoDTO;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.vo.FileInfoVo;
import com.easy.system.file.vo.UserResourcesVo;

/**
 * 附件-服务类
 * @author
 * @time
 */
public interface  IFileInfoService extends IBaseService<FileInfo> {



	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param fileInfo
	 */
	public UserResourcesVo saveOrUpdateFileInfo(FileInfo fileInfo,String parentId,boolean speedPass);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FileInfoVo> queryFileInfoPage(IPage<FileInfoVo> page,FileInfoDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FileInfoVo> queryFileInfoAll(FileInfoDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public FileInfoVo queryFileInfoByCondition(FileInfoDTO param);
	//================代码生成器生成代码结束=================
}
