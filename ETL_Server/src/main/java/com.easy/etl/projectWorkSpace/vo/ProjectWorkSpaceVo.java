package com.easy.etl.projectWorkSpace.vo;


import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;

/**
*  项目工作空间-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectWorkSpaceVo extends ProjectWorkSpace {
    private String workSpaceTypeName;

    public String getWorkSpaceTypeName() {
        return workSpaceTypeName;
    }

    public void setWorkSpaceTypeName(String workSpaceTypeName) {
        this.workSpaceTypeName = workSpaceTypeName;
    }
}
