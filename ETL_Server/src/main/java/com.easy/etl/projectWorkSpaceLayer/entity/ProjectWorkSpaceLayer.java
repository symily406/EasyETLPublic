package com.easy.etl.projectWorkSpaceLayer.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目工作空间分层-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_WORK_SPACE_LAYER
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_WORK_SPACE_LAYER", autoResultMap = true)
public class ProjectWorkSpaceLayer extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "WORK_SPACE_LAYER_ID", type = IdType.ASSIGN_ID)
    private String workSpaceLayerId;

    /**
     * 名称
     */
    @TableField(value = "WORK_SPACE_LAYER_NAME")
    @NotEmpty(message = "workSpaceLayerName名称不能为空")
    @Length(max = 64, message = "workSpaceLayerName名称不能超过64个字符")
    private String workSpaceLayerName;


    public ProjectWorkSpaceLayer() {
        super();
    }

    /**
     * 主键-数据库字段:WORK_SPACE_LAYER_ID
     */
    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }

    /**
     * 主键-数据库字段:WORK_SPACE_LAYER_ID
     */
    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }


    /**
     * 名称-数据库字段:WORK_SPACE_LAYER_NAME
     */
    public String getWorkSpaceLayerName() {
        return workSpaceLayerName;
    }

    /**
     * 名称-数据库字段:WORK_SPACE_LAYER_NAME
     */
    public void setWorkSpaceLayerName(String workSpaceLayerName) {
        this.workSpaceLayerName = workSpaceLayerName;
    }


}
