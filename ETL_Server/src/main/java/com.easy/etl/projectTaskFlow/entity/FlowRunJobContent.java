package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 工作流运行JOB_内容-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_RUN_JOB_CONTENT
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_RUN_JOB_CONTENT", autoResultMap = true)
public class FlowRunJobContent extends Model<FlowRunJobContent> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "FLOW_RUN_JOB_ID", type = IdType.ASSIGN_ID)
    private String flowRunJobId;

    /**
     * 归属工作流JOB
     */
    @TableField(value = "FLOW_JOB_ID")
    @NotEmpty(message = "flowJobId归属工作流JOB不能为空")
    @Length(max = 32, message = "flowJobId归属工作流JOB不能超过32个字符")
    private String flowJobId;
    /**
     * 流程内容
     */
    @TableField(value = "FLOW_CONTENT")
    @Length(max = 0, message = "flowContent流程内容不能超过0个字符")
    private String flowContent;


    public FlowRunJobContent() {
        super();
    }

    /**
     * 主键-数据库字段:FLOW_RUN_JOB_ID
     */
    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    /**
     * 主键-数据库字段:FLOW_RUN_JOB_ID
     */
    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }


    /**
     * 归属工作流JOB-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 归属工作流JOB-数据库字段:FLOW_JOB_ID
     */
    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }

    /**
     * 流程内容-数据库字段:FLOW_CONTENT
     */
    public String getFlowContent() {
        return flowContent;
    }

    /**
     * 流程内容-数据库字段:FLOW_CONTENT
     */
    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }


}
