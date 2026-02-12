package com.easy.etl.backup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 项目工作流节点备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_FLOW_NODE_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_FLOW_NODE_BACKUP", autoResultMap = true)
public class ProjectTaskFlowNodeBackup extends ProjectTaskFlowNode {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;



    /**
     * 备份来源节点ID
     */
    @TableField(value = "BACKUP_NODE_ID")
    @Length(max = 64, message = "backupNodeId备份来源节点ID不能超过64个字符")
    private String backupNodeId;

    /**
     * 备份来源归属任务
     */
    @TableField(value = "BACKUP_TASK_ID")
    @NotEmpty(message = "backupTaskId备份来源归属任务不能为空")
    @Length(max = 32, message = "backupTaskId备份来源归属任务不能超过32个字符")
    private String backupTaskId;

    /**
     * 备份来源执行任务ID
     */
    @TableField(value = "BACKUP_EXECUTE_TASK_ID")
    @Length(max = 32, message = "backupExecuteTaskId备份来源执行任务ID不能超过32个字符")
    private String backupExecuteTaskId;



    public ProjectTaskFlowNodeBackup() {
        super();
    }

    public String getBackupNodeId() {
        return backupNodeId;
    }

    public void setBackupNodeId(String backupNodeId) {
        this.backupNodeId = backupNodeId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public String getBackupExecuteTaskId() {
        return backupExecuteTaskId;
    }

    public void setBackupExecuteTaskId(String backupExecuteTaskId) {
        this.backupExecuteTaskId = backupExecuteTaskId;
    }


}
