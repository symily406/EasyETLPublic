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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "FLOW_RUN_JOB_ID", type = IdType.ASSIGN_ID)
    private String flowRunJobId;


    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;

    @TableField(value = "FLOW_CONTENT")
    private String flowContent;


    public FlowRunJobContent() {
        super();
    }


    public String getFlowRunJobId() {
        return flowRunJobId;
    }


    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }



    public String getFlowJobId() {
        return flowJobId;
    }


    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }


    public String getFlowContent() {
        return flowContent;
    }


    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }


}
