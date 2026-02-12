package com.easy.system.file.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.dto.FileFolderDTO;
import com.easy.system.file.entity.FileFolder;
import com.easy.system.file.mapper.FileConfigMapper;
import com.easy.system.file.mapper.FileFolderMapper;
import com.easy.system.file.mapper.FileInfoMapper;
import com.easy.system.file.service.IFileFolderService;
import com.easy.system.file.vo.FileConfigVo;
import com.easy.system.file.vo.FileFolderTreeVo;
import com.easy.system.file.vo.FileFolderVo;

/**
 * 文件夹-服务类
 *
 * @author
 * @time
 */
@Service
public class FileFolderServiceImpl extends BaseServiceImpl<FileFolderMapper, FileFolder> implements IFileFolderService {

	@Autowired
	private FileConfigMapper fileConfigMapper;


	/**
	 * 删除文件夹
	 *
	 * @param folderId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteFolder(String folderId) {
		baseMapper.deleteById(folderId);
		return true;
	}

	/**
	 * 文件夹树
	 *
	 * @param fileConfigDTO
	 * @param fileFolderDTO
	 * @return
	 */
	@Override
	public List<FileFolderTreeVo> folderTreeVos(FileConfigDTO fileConfigDTO, FileFolderDTO fileFolderDTO) {
		List<FileFolderTreeVo> re = new ArrayList<>();
		List<FileConfigVo> configVos = fileConfigMapper.queryFileConfigAll(fileConfigDTO);
		List<FileFolderVo> folderVos = baseMapper.queryFileFolderAll(fileFolderDTO);
		for (FileConfigVo configVo : configVos) {
			FileFolderTreeVo vo = new FileFolderTreeVo();
			vo.setId(configVo.getConfigId());
			vo.setConfigId(configVo.getConfigId());
			vo.setFileType(configVo.getFileType());
			vo.setAccept(configVo.getAccept());
			vo.setChunkSize(configVo.getChunkSize());
			vo.setName(configVo.getName());
			vo.setIsFolder(0);
			vo.setFolderId("0");
			vo.setParentFolderId("0");
			List<FileFolderVo> childrenFolder = folderVos.stream()
					.filter(v -> v.getConfigId().equals(configVo.getConfigId())).collect(Collectors.toList()).stream()
					.filter(v -> v.getParentFolderId().equals("0")).collect(Collectors.toList());

			vo.setChildren(getChildren(configVo, childrenFolder, folderVos));
			re.add(vo);
		}
		return re;
	}

	private List<FileFolderTreeVo> getChildren(FileConfigVo configVo, List<FileFolderVo> childrenFolder,
			List<FileFolderVo> folderVos) {
		List<FileFolderTreeVo> reChildren = new ArrayList<>();
		if (childrenFolder != null && !childrenFolder.isEmpty()) {
			for (FileFolderVo folder : childrenFolder) {
				FileFolderTreeVo vo = new FileFolderTreeVo();
				vo.setId(folder.getFolderId());
				vo.setConfigId(folder.getConfigId());
				vo.setFolderId(folder.getFolderId());
				vo.setName(folder.getFolderName());
				vo.setFileType(configVo.getFileType());
				vo.setAccept(configVo.getAccept());
				vo.setChunkSize(configVo.getChunkSize());
				vo.setIsFolder(1);
				vo.setParentFolderId(folder.getParentFolderId());
				vo.setChildren(getChildren(configVo, folderVos.stream()
						.filter(v -> v.getParentFolderId().equals(folder.getFolderId())).collect(Collectors.toList()),
						folderVos));
				reChildren.add(vo);
			}
		}
		return reChildren;
	}

	/**
	 * 保存或修改Entity
	 *
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateFileFolder(FileFolder fileFolder) {
		return saveOrUpdate(fileFolder);
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<FileFolderVo> queryFileFolderPage(IPage<FileFolderVo> page, FileFolderDTO param) {
		return page.setRecords(baseMapper.queryFileFolderPage(page, param));
	}

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	@Override
	public List<FileFolderVo> queryFileFolderAll(FileFolderDTO param) {
		return baseMapper.queryFileFolderAll(param);
	}

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	@Override
	public FileFolderVo queryFileFolderByCondition(FileFolderDTO param) {
		return baseMapper.queryFileFolderByCondition(param);
	}

}
