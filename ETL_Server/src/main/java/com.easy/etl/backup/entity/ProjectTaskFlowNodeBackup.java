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


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_NODE_ID")
    private String backupNodeId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    @TableField(value = "BACKUP_EXECUTE_TASK_ID")
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
