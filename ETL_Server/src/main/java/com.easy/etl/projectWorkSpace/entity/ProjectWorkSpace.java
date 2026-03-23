package com.easy.etl.projectWorkSpace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目工作空间-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_WORK_SPACE
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_WORK_SPACE", autoResultMap = true)
public class ProjectWorkSpace extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "WORK_SPACE_ID", type = IdType.ASSIGN_ID)
    private String workSpaceId;


    @TableField(value = "WORK_SPACE_NAME")
    private String workSpaceName;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;


    public ProjectWorkSpace() {
        super();
    }


    public String getWorkSpaceId() {
        return workSpaceId;
    }


    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }



    public String getWorkSpaceName() {
        return workSpaceName;
    }


    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }


    public String getWorkSpaceType() {
        return workSpaceType;
    }


    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }


}
