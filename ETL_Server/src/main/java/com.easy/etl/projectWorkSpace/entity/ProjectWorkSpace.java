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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "WORK_SPACE_ID", type = IdType.ASSIGN_ID)
    private String workSpaceId;

    /**
     * 名称
     */
    @TableField(value = "WORK_SPACE_NAME")
    @NotEmpty(message = "workSpaceName名称不能为空")
    @Length(max = 64, message = "workSpaceName名称不能超过64个字符")
    private String workSpaceName;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;


    public ProjectWorkSpace() {
        super();
    }

    /**
     * 主键-数据库字段:WORK_SPACE_ID
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    /**
     * 主键-数据库字段:WORK_SPACE_ID
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }


    /**
     * 名称-数据库字段:WORK_SPACE_NAME
     */
    public String getWorkSpaceName() {
        return workSpaceName;
    }

    /**
     * 名称-数据库字段:WORK_SPACE_NAME
     */
    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public String getWorkSpaceType() {
        return workSpaceType;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }


}
