package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 工作流JOB_内容-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_JOB_CONTENT
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_JOB_CONTENT", autoResultMap = true)
public class FlowJobContent extends Model<FlowJobNode> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "FLOW_JOB_ID", type = IdType.ASSIGN_ID)
    private String flowJobId;

    /**
     * 流程内容
     */
    @TableField(value = "FLOW_CONTENT")
    @Length(max = 0, message = "flowContent流程内容不能超过0个字符")
    private String flowContent;


    public FlowJobContent() {
        super();
    }

    /**
     * 主键-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 主键-数据库字段:FLOW_JOB_ID
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
