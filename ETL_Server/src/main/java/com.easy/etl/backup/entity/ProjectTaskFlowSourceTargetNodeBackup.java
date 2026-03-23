package com.easy.etl.backup.entity;

import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目工作流节点关系备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_FLOW_SOURCE_TARGET_NODE_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_FLOW_SOURCE_TARGET_NODE_BACKUP", autoResultMap = true)
public class ProjectTaskFlowSourceTargetNodeBackup extends ProjectTaskFlowSourceTargetNode {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_ID")
    private String backupId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    @TableField(value = "BAKE_SOURCE_NODE_ID")
    private String bakeSourceNodeId;


    @TableField(value = "BAKE_TARGET_NODE_ID")
    private String bakeTargetNodeId;


    public ProjectTaskFlowSourceTargetNodeBackup() {
        super();
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public String getBakeSourceNodeId() {
        return bakeSourceNodeId;
    }

    public void setBakeSourceNodeId(String bakeSourceNodeId) {
        this.bakeSourceNodeId = bakeSourceNodeId;
    }

    public String getBakeTargetNodeId() {
        return bakeTargetNodeId;
    }

    public void setBakeTargetNodeId(String bakeTargetNodeId) {
        this.bakeTargetNodeId = bakeTargetNodeId;
    }
}
