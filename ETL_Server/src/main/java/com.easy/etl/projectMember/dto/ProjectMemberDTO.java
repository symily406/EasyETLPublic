package com.easy.etl.projectMember.dto;


import com.easy.etl.projectMember.entity.ProjectMember;

/**
*  项目成员-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectMemberDTO extends ProjectMember {
    private String projectWorkSpaceId;

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }
}
