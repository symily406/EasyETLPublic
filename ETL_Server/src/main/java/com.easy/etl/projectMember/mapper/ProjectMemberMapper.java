package com.easy.etl.projectMember.mapper;


import java.util.List;

import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.entity.ProjectMember;
import com.easy.etl.projectMember.vo.ProjectMemberVo;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目成员-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectMemberMapper extends MapperDao<ProjectMember> {


    List<ProjectMemberWorkSpaceVo> queryProjectMemberWorkSpace(@Param("param") ProjectMemberDTO param);

    /**
     * 查询人员工作空间目录层级
     * @param param
     * @return
     */
    List<ProjectMemberWorkSpaceVo> queryProjectMemberWorkSpaceLayer(@Param("param") ProjectMemberDTO param);

    //=============代码生成器生成代码开始==============

    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<ProjectMemberVo> queryProjectMemberPage(IPage<ProjectMemberVo> page, @Param("param") ProjectMemberDTO param);


    //=============代码生成器生成代码结束==============
}
