package com.easy.etl.projectMember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目成员-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_MEMBER
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_MEMBER", autoResultMap = true)
public class ProjectMember extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "MEMBER_ID", type = IdType.ASSIGN_ID)
    private String memberId;

    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 用户ID
     */
    @TableField(value = "STAFF_ID")
    @Length(max = 32, message = "staffId用户ID不能超过32个字符")
    private String staffId;


    public ProjectMember() {
        super();
    }

    /**
     * 主键-数据库字段:MEMBER_ID
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 主键-数据库字段:MEMBER_ID
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


}
