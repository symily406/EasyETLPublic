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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "MEMBER_ID", type = IdType.ASSIGN_ID)
    private String memberId;


    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "STAFF_ID")
    private String staffId;


    public ProjectMember() {
        super();
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


    public String getStaffId() {
        return staffId;
    }


    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


}
