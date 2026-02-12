package com.easy.etl.projectMember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目成员工作空间层级关联表-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_MEMBER_SPACE_LAYER_REL
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_MEMBER_SPACE_LAYER_REL", autoResultMap = true)
public class ProjectMemberSpaceLayerRel extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "REL_ID", type = IdType.ASSIGN_ID)
    private String relId;

    /**
     * 项目成员ID
     */
    @TableField(value = "MEMBER_ID")
    @NotEmpty(message = "memberId项目成员ID不能为空")
    @Length(max = 32, message = "memberId项目成员ID不能超过32个字符")
    private String memberId;
    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 项目空间层级ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @NotEmpty(message = "projectWorkSpaceLayerRelId项目空间层级ID不能为空")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId项目空间层级ID不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;
    /**
     * 用户ID
     */
    @TableField(value = "STAFF_ID")
    @Length(max = 32, message = "staffId用户ID不能超过32个字符")
    private String staffId;
    /**
     * 工作空间
     */
    @TableField(value = "WORK_SPACE_ID")
    @NotEmpty(message = "workSpaceId工作空间不能为空")
    @Length(max = 32, message = "workSpaceId工作空间不能超过32个字符")
    private String workSpaceId;
    /**
     * 分层
     */
    @TableField(value = "WORK_SPACE_LAYER_ID")
    @NotEmpty(message = "workSpaceLayerId分层不能为空")
    @Length(max = 32, message = "workSpaceLayerId分层不能超过32个字符")
    private String workSpaceLayerId;


    public ProjectMemberSpaceLayerRel() {
        super();
    }

    /**
     * 主键-数据库字段:REL_ID
     */
    public String getRelId() {
        return relId;
    }

    /**
     * 主键-数据库字段:REL_ID
     */
    public void setRelId(String relId) {
        this.relId = relId;
    }


    /**
     * 项目成员ID-数据库字段:MEMBER_ID
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 项目成员ID-数据库字段:MEMBER_ID
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 项目空间层级ID-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间层级ID-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 用户ID-数据库字段:STAFF_ID
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * 用户ID-数据库字段:STAFF_ID
     */
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    /**
     * 工作空间-数据库字段:WORK_SPACE_ID
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    /**
     * 工作空间-数据库字段:WORK_SPACE_ID
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    /**
     * 分层-数据库字段:WORK_SPACE_LAYER_ID
     */
    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }

    /**
     * 分层-数据库字段:WORK_SPACE_LAYER_ID
     */
    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }


}
