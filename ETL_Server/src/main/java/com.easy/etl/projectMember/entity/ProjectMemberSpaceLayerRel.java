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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "REL_ID", type = IdType.ASSIGN_ID)
    private String relId;


    @TableField(value = "MEMBER_ID")
    private String memberId;

    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    private String projectWorkSpaceLayerRelId;

    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;

    @TableField(value = "STAFF_ID")
    private String staffId;

    @TableField(value = "WORK_SPACE_ID")
    private String workSpaceId;

    @TableField(value = "WORK_SPACE_LAYER_ID")
    private String workSpaceLayerId;


    public ProjectMemberSpaceLayerRel() {
        super();
    }


    public String getRelId() {
        return relId;
    }


    public void setRelId(String relId) {
        this.relId = relId;
    }



    public String getMemberId() {
        return memberId;
    }


    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }


    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }


    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }


    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }


    public String getStaffId() {
        return staffId;
    }


    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


    public String getWorkSpaceId() {
        return workSpaceId;
    }


    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }


    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }


    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }


}
