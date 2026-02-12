package com.easy.etl.projectWorkSpace.vo;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easy.etl.project.entity.Project;
import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ProjectWorkSpaceTreeVo extends ProjectWorkSpace {

    private String id;

    private String label;

    private String name;

    private String projectId;

    private String projectWorkSpaceId;



    private List<ProjectWorkSpaceLayerVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return getWorkSpaceName();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return getWorkSpaceName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectWorkSpaceLayerVo> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectWorkSpaceLayerVo> children) {
        this.children = children;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }
}
