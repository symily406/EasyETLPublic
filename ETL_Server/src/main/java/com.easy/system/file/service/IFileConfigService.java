package com.easy.system.file.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.vo.FileConfigVo;

/**
 * 文件上传配置-服务类
 * @author
 * @time
 */
public interface  IFileConfigService extends IBaseService<FileConfig> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param fileConfig
	 */
	public boolean saveOrUpdateFileConfig(FileConfig fileConfig);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FileConfigVo> queryFileConfigPage(IPage<FileConfigVo> page,FileConfigDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FileConfigVo> queryFileConfigAll(FileConfigDTO param);

	/**
	 * 查询上传配置
	 * @param param
	 * @return
	 */
	String queryFileAccept(FileConfigDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public FileConfigVo queryFileConfigByCondition(FileConfigDTO param);
	//================代码生成器生成代码结束=================
}
