package com.easy.system.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.boot.enums.HttpEnum;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.PathKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import com.easy.etl.core.init.Config;
import com.easy.system.file.dto.FileChunkDTO;
import com.easy.system.file.entity.FileChunk;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.service.IFileChunkService;
import com.easy.system.file.service.IFileConfigService;
import com.easy.system.file.service.IFileInfoService;
import com.easy.system.file.vo.UserResourcesVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.io.FileUtil;

/**
 * 文件上传分片-控制器
 *
 * @author
 * @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/file/fileChunk")
public class FileChunkController extends BaseController {
	private Logger log = LoggerFactory.getLogger(FileChunkController.class);

	private FileOutputStream outputStream;

	private FileInputStream fileInputStream;

	private UserResourcesVo userResourcesVo;

	private FileInfo fileInfo;

	@Autowired
	private IFileChunkService fileChunkService;

	@Autowired
	private IFileInfoService fileInfoService;
	@Autowired
	private IFileConfigService fileConfigService;


	@SaCheckLogin
	@GetMapping("/upload")
	@SaCheckPermission(value  ={ "admin:system:file:fileChunk:upload" })
	public ApiResult upload(FileChunkDTO chunkDTO, HttpServletResponse response, HttpServletRequest request) {


		String parentId = request.getHeader("parentId");

		FileInfo fileInfo = fileInfoService
				.getOne(new QueryWrapper<FileInfo>().lambda().eq(FileInfo::getMd5, chunkDTO.getIdentifier()), false);
		if (fileInfo != null) {
			chunkDTO.setSkip(true);
			return ApiResult.ok(chunkDTO);
		}

		List<FileChunk> fileChunks = fileChunkService
				.list(new QueryWrapper<FileChunk>().lambda().eq(FileChunk::getIdentifier, chunkDTO.getIdentifier()));
		if (fileChunks != null && !fileChunks.isEmpty()) {
			fileChunkService.updateChunkParentId(chunkDTO.getIdentifier(), parentId);
		}
		if (fileChunks.size() == chunkDTO.getTotalChunks()) {
			chunkDTO.setSkip(true);
			return ApiResult.ok(chunkDTO);
		}
		List<Integer> uploadedChunks = fileChunks.stream().map(FileChunk::getChunkNumber).collect(Collectors.toList());
		chunkDTO.setUploadedChunks(uploadedChunks);
		return ApiResult.ok(chunkDTO);
	}

	@SaCheckLogin
	@DebugAnnotation(desc = "移除上传文件")
	@GetMapping(value = "/fileRemoved")
	@SaCheckPermission(value  ={ "admin:system:file:fileChunk:upload" })
	public ApiResult fileRemoved(@NotNull(message = "分片标识不能为空") String identifier) {
		fileChunkService.fileRemoved(identifier);
		return ApiResult.ok();
	}

	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传")
	@PostMapping(value = "/upload")
	@SaCheckPermission(value  ={ "admin:system:file:fileChunk:upload" })
	public ApiResult upload(@RequestParam("file") MultipartFile file, FileChunk fileChunk, HttpServletRequest request,
			HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		String parentId = request.getHeader("parentId");
		fileChunk.setParentId(parentId);
		response.setStatus(HttpServletResponse.SC_OK);
		boolean succed = true;
		try {
			if (file.isEmpty()) {
				succed = false;
			} else {
				// 获取文件名
				String fileName = file.getOriginalFilename();
				log.info("上传的文件名为：" + fileName);
				// 获取文件的后缀名
				String suffixName = FileUtil.extName(file.getOriginalFilename());
				log.info("文件的后缀名为：" + suffixName);

				// 构造一个路径
				String saveName = fileChunk.getIdentifier().concat("_")
						.concat(String.valueOf(fileChunk.getChunkNumber())).concat(".").concat(suffixName);
				File saveFolder = FileUtil
						.mkdir(PathKit.handle(Config.chunkPath.concat("/").concat(fileChunk.getIdentifier())));
				String savePath = PathKit.handle(String.valueOf(saveFolder)).concat("/").concat(saveName);
				fileChunk.setFileName(fileName);
				fileChunk.setSavePath(savePath);
				// 文件写入
				file.transferTo(FileUtil.file(savePath));
				fileChunk.setMd5(fileChunk.getIdentifier());
				fileChunkService.save(fileChunk);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			succed = false;
		} catch (IOException e) {
			e.printStackTrace();
			succed = false;
		}
		if (succed) {
			return ApiResult.ok();
		}
		return ApiResult.result(HttpEnum.UPLOAD_ERROR.getCode(), HttpEnum.UPLOAD_ERROR.getMsg(),
				HttpEnum.UPLOAD_ERROR.getSucced(), fileChunk);
	}

	@SaCheckLogin
	@DebugAnnotation(desc = "文件合并")
	@GetMapping(value = "/merge")
	@SaCheckPermission(value  ={ "admin:system:file:fileChunk:upload" })
	public ApiResult merge(@NotNull(message = "分片标识不能为空") String identifier,
			@NotNull(message = "上级分类不能为空") String parentId) {

		fileInfo = fileInfoService.getOne(new QueryWrapper<FileInfo>().lambda().eq(FileInfo::getMd5, identifier),
				false);
		if (fileInfo != null) {// 秒传
			userResourcesVo = fileInfoService.saveOrUpdateFileInfo(fileInfo, parentId, true);
			if (userResourcesVo != null) {
				return ApiResult.ok(userResourcesVo);
			}
			return ApiResult.fail("文件上传失败");
		}


		List<FileChunk> list = fileChunkService.list(new QueryWrapper<FileChunk>().lambda()
				.eq(FileChunk::getIdentifier, identifier).orderByAsc(FileChunk::getChunkNumber));

		if (list != null && !list.isEmpty()) {
			FileChunk fileChunk = list.get(0);
			String fileName = fileChunk.getFileName();
			String companyId = fileChunk.getCompanyId();
			String orgId = fileChunk.getOrgId();
			String userId = fileChunk.getUserId();
			String md5 = fileChunk.getMd5();

			String fileExt = FileUtil.extName(fileName);
			String saveName = String.valueOf(IdBulider.builder()).concat(".").concat(fileExt);
			String saveFolder = PathKit.handle("/".concat(DateFormatUtils.format(new Date(), "yyyy")).concat("/")
					.concat(DateFormatUtils.format(new Date(), "MM")).concat("/")
					.concat(DateFormatUtils.format(new Date(), "dd")).concat("/")
					.concat(DateFormatUtils.format(new Date(), "HH")));
			String absPath = String
					.valueOf(FileUtil
							.mkdir(FileUtil.file(PathKit.handle(Config.savePath.concat("/").concat(saveFolder)))))
					.concat("/").concat(saveName);

			if (fileChunk.getTotalChunks() == list.size()) {
				try {
					boolean succed = true;
					outputStream = new FileOutputStream(FileUtil.file(absPath), true);
					byte[] byt = new byte[10 * 1024 * 1024];
					int len = 0;
					for (FileChunk chunk : list) {
						File chunkFile = FileUtil.file(chunk.getSavePath());
						if (!chunkFile.exists()) {
							succed = false;
							break;
						}
						fileInputStream = new FileInputStream(chunkFile);
						while ((len = fileInputStream.read(byt)) != -1) {
							outputStream.write(byt, 0, len);
						}
						fileInputStream.close();
					}
					if (succed) {

						fileInfo = new FileInfo();
						fileInfo.setMd5(md5);
						fileInfo.setSaveName(saveName);
						fileInfo.setFileName(fileName);
						fileInfo.setSaveFolder(saveFolder);
						fileInfo.setSuffix(fileExt.toLowerCase());
						fileInfo.setUserCompanyId(companyId);
						fileInfo.setCompanyId(companyId);
						fileInfo.setUserOrgId(orgId);
						fileInfo.setUserId(userId);
						fileInfo.setFileSize(fileChunk.getTotalSize());
						userResourcesVo = fileInfoService.saveOrUpdateFileInfo(fileInfo, parentId, false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (fileInputStream != null) {
							fileInputStream.close();
						}
						if (outputStream != null) {
							outputStream.close();
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			}
			// 删除分片信息
			fileChunkService.deleteFileChunkById(fileChunk);
		}
		if (userResourcesVo != null) {
			return ApiResult.ok(userResourcesVo);
		}
		return ApiResult.fail("文件合并失败");
	}
}
