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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "WORK_SPACE_LAYER_ID", type = IdType.ASSIGN_ID)
    private String workSpaceLayerId;


    @TableField(value = "WORK_SPACE_LAYER_NAME")
    private String workSpaceLayerName;


    public ProjectWorkSpaceLayer() {
        super();
    }


    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }


    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }



    public String getWorkSpaceLayerName() {
        return workSpaceLayerName;
    }


    public void setWorkSpaceLayerName(String workSpaceLayerName) {
        this.workSpaceLayerName = workSpaceLayerName;
    }


}
