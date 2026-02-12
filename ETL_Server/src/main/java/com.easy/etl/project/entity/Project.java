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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "PROJECT_ID", type = IdType.ASSIGN_ID)
    private String projectId;

    /**
     * 项目名称
     */
    @TableField(value = "PROJECT_NAME")
    @NotEmpty(message = "projectName项目名称不能为空")
    @Length(max = 128, message = "projectName项目名称不能超过128个字符")
    private String projectName;




    public Project() {
        super();
    }

    /**
     * 主键-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 主键-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    /**
     * 项目名称-数据库字段:PROJECT_NAME
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称-数据库字段:PROJECT_NAME
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }




}
