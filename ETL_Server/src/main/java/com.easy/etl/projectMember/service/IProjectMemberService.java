package com.easy.etl.projectMember.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.entity.ProjectMember;
import com.easy.etl.projectMember.vo.ProjectMemberVo;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

import java.util.List;

/**
 * 项目成员-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectMemberService extends IBaseService<ProjectMember> {

	/**
	 * 查询用户工作空间
	 * @return
	 */
	public List<ProjectMemberWorkSpaceVo> queryProjectMemberWorkSpace(ProjectMemberDTO param);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param projectMember
	 */
	public boolean saveOrUpdateProjectMember(ProjectMember projectMember,String memberArrs);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectMemberVo> queryProjectMemberPage(IPage<ProjectMemberVo> page, ProjectMemberDTO param);

	//================代码生成器生成代码结束=================
}
