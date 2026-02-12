package com.easy.etl.project.service;


import java.util.List;

import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.project.vo.ProjectWorkSpaceLayerRelVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作空间分层关联-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectWorkSpaceLayerRelService extends IBaseService<ProjectWorkSpaceLayerRel> {

    public List<ProjectWorkSpaceLayerRelVo> queryProjectWorkSpaceLayer(String projectId);
}
