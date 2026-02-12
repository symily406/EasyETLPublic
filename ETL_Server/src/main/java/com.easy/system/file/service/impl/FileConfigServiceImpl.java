package com.easy.system.file.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.run.cache.CacheManagement;
import com.easy.etl.core.init.Config;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.mapper.FileConfigMapper;
import com.easy.system.file.service.IFileConfigService;
import com.easy.system.file.vo.FileConfigVo;

import cn.hutool.crypto.SecureUtil;

/**
 * 文件上传配置-服务类
 * @author
 * @time
 */
@Service
public class FileConfigServiceImpl extends BaseServiceImpl<FileConfigMapper, FileConfig> implements IFileConfigService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateFileConfig(FileConfig fileConfig) {
		return saveOrUpdate(fileConfig);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<FileConfigVo> queryFileConfigPage(IPage<FileConfigVo> page, FileConfigDTO param) {
		return page.setRecords(baseMapper.queryFileConfigPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public String queryFileAccept(FileConfigDTO param) {
		String fileAccept=CacheManagement.getStaffFileConfig(param.getCompanyId());
		if(StringUtils.isNotBlank(fileAccept)) {
			return fileAccept;
		}
		fileAccept="*.*";
		List<FileConfigVo> configVos=baseMapper.queryFileConfigAll(param);
		if (configVos!=null&&!configVos.isEmpty()) {
			fileAccept=configVos.stream().map(FileConfigVo::getAccept).collect(Collectors.joining(","));
		}
		CacheManagement.setStaffFileConfig(param.getCompanyId(), fileAccept);
		return fileAccept;
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<FileConfigVo> queryFileConfigAll(FileConfigDTO param) {
		return baseMapper.queryFileConfigAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public FileConfigVo queryFileConfigByCondition(FileConfigDTO param) {
		return baseMapper.queryFileConfigByCondition(param);
	}
}
