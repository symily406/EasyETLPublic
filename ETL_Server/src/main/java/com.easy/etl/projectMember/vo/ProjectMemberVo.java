package com.easy.etl.projectMember.vo;


import com.easy.etl.projectMember.entity.ProjectMember;

/**
*  项目成员-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectMemberVo extends ProjectMember {

    private  String projectName;
    private  String userName;
    private  String phone;

    private String name;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
