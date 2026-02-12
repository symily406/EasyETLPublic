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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;



    /**
     * 来源ID
     */
    @TableField(value = "BACKUP_ID")
    @Length(max = 32, message = "backupId来源ID不能超过32个字符")
    private String backupId;

    /**
     * 备份归属任务
     */
    @TableField(value = "BACKUP_TASK_ID")
    @NotEmpty(message = "backupTaskId备份归属任务不能为空")
    @Length(max = 32, message = "backupTaskId备份归属任务不能超过32个字符")
    private String backupTaskId;

    /**
     * 备份来源节点
     */
    @TableField(value = "BAKE_SOURCE_NODE_ID")
    @Length(max = 64, message = "bakeSourceNodeId备份来源节点不能超过64个字符")
    private String bakeSourceNodeId;

    /**
     * 备份目标节点
     */
    @TableField(value = "BAKE_TARGET_NODE_ID")
    @Length(max = 64, message = "bakeTargetNodeId备份目标节点不能超过64个字符")
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
