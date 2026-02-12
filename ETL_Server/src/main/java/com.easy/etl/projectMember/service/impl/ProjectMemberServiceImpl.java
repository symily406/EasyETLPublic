package com.easy.etl.projectMember.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.entity.ProjectMember;
import com.easy.etl.projectMember.entity.ProjectMemberSpaceLayerRel;
import com.easy.etl.projectMember.mapper.ProjectMemberMapper;
import com.easy.etl.projectMember.mapper.ProjectMemberSpaceLayerRelMapper;
import com.easy.etl.projectMember.service.IProjectMemberService;
import com.easy.etl.projectMember.vo.ProjectMemberVo;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目成员-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectMemberServiceImpl extends BaseServiceImpl<ProjectMemberMapper, ProjectMember> implements IProjectMemberService {

	@Autowired
	private ProjectMemberSpaceLayerRelMapper projectMemberSpaceLayerRelMapper;

	@Override
	public List<ProjectMemberWorkSpaceVo> queryProjectMemberWorkSpace(ProjectMemberDTO param) {
		return baseMapper.queryProjectMemberWorkSpace(param);
	}

	/**
	     * 保存或修改Entity
	 * @param projectMember
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateProjectMember(ProjectMember projectMember,String memberArrs) {

		String memberId=SecureUtil.md5(projectMember.getProjectId().concat("_").concat(projectMember.getStaffId()));

		List<ProjectMemberSpaceLayerRel> rels= JsonKit.parseList(memberArrs, ProjectMemberSpaceLayerRel.class);

		projectMemberSpaceLayerRelMapper.update(null,new UpdateWrapper<ProjectMemberSpaceLayerRel>().lambda()
				.set(ProjectMemberSpaceLayerRel::getEnable,0)
				.eq(ProjectMemberSpaceLayerRel::getMemberId,memberId)
		);

		for(ProjectMemberSpaceLayerRel rel:rels){
			String relId= SecureUtil.md5(memberId.concat("_")
					.concat(rel.getProjectWorkSpaceLayerRelId()).concat("_")
					.concat(rel.getProjectWorkSpaceId()).concat("_"));
			rel.setRelId(relId);
			rel.setProjectId(projectMember.getProjectId());
			rel.setStaffId(projectMember.getStaffId());
			rel.setEnable(1);
			rel.setMemberId(memberId);

			if(projectMemberSpaceLayerRelMapper.selectById(relId)==null){
				projectMemberSpaceLayerRelMapper.insert(rel);
				continue;
			}
			projectMemberSpaceLayerRelMapper.updateById(rel);
		}
		projectMember.setMemberId(memberId);
		return saveOrUpdate(projectMember);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<ProjectMemberVo> queryProjectMemberPage(IPage<ProjectMemberVo> page, ProjectMemberDTO param) {
		return page.setRecords(baseMapper.queryProjectMemberPage(page, param));
	}

}
