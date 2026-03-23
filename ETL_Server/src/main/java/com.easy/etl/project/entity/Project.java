package com.easy.etl.project.entity;

import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目管理-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT", autoResultMap = true)
public class Project extends BaseEntity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "PROJECT_ID", type = IdType.ASSIGN_ID)
    private String projectId;


    @TableField(value = "PROJECT_NAME")
    private String projectName;




    public Project() {
        super();
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }



    public String getProjectName() {
        return projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }




}
