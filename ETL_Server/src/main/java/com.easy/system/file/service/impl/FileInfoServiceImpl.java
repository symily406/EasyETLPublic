package com.easy.system.file.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.file.dto.FileInfoDTO;
import com.easy.system.file.dto.UserResourcesDTO;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.entity.UserResources;
import com.easy.system.file.mapper.FileChunkMapper;
import com.easy.system.file.mapper.FileConfigMapper;
import com.easy.system.file.mapper.FileInfoMapper;
import com.easy.system.file.mapper.UserResourcesMapper;
import com.easy.system.file.service.IFileInfoService;
import com.easy.system.file.vo.FileInfoVo;
import com.easy.system.file.vo.UserResourcesVo;

/**
 * 附件-服务类
 * @author
 * @time
 */
@Service
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

	@Autowired
	private FileConfigMapper fileConfigMapper;

	@Autowired
	private UserResourcesMapper userResourcesMapper;

	@Autowired
	private FileChunkMapper fileChunkMapper;



	/**
	     * 保存或修改Entity
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public UserResourcesVo saveOrUpdateFileInfo(FileInfo fileInfo,String parentId,boolean speedPass) {
		FileConfig fileConfig=fileConfigMapper.selectOne(new QueryWrapper<FileConfig>().lambda()
				.like(FileConfig::getAccept, fileInfo.getSuffix())
				.eq(FileConfig::getCompanyId, fileInfo.getCompanyId())
				);
		if (fileConfig==null) {
			return null;
		}
		if (!speedPass) {//秒传时不添加文件信息
			fileInfo.setEnable(1);
			baseMapper.insert(fileInfo);
		}

		UserResources folder=userResourcesMapper.selectById(parentId);
		UserResources resources=new UserResources();
		resources.setId(idBulider.id());
		resources.setName(fileInfo.getFileName());
		resources.setParentId(parentId);
		resources.setFullPath(StrKit.clearDuplicateValue("0".concat("|").concat(resources.getId()),"|"));
		if (folder!=null) {
			resources.setFullPath(StrKit.clearDuplicateValue(resources.getParentId().concat("|").concat(folder.getFullPath()).concat("|").concat(resources.getId()),"|"));
		}
		resources.setSourceType(fileConfig.getFileType());
		resources.setFileId(fileInfo.getFileId());
		resources.setUserCompanyId(fileInfo.getUserCompanyId());
		resources.setUserOrgId(fileInfo.getUserOrgId());
		resources.setUserId(fileInfo.getUserId());
		resources.setEnable(1);

		userResourcesMapper.insert(resources);

		UserResourcesDTO param=new UserResourcesDTO();
		param.setId(resources.getId());

		UserResourcesVo vo=userResourcesMapper.queryUserResourcesByCondition(param);
		vo.setSpeedPass(speedPass);
		return vo;
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<FileInfoVo> queryFileInfoPage(IPage<FileInfoVo> page, FileInfoDTO param) {
		return page.setRecords(baseMapper.queryFileInfoPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<FileInfoVo> queryFileInfoAll(FileInfoDTO param) {
		return baseMapper.queryFileInfoAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public FileInfoVo queryFileInfoByCondition(FileInfoDTO param) {
		return baseMapper.queryFileInfoByCondition(param);
	}
}
