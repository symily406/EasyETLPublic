package com.easy.etl.project.service.impl;
import java.util.List;

import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.project.mapper.ProjectWorkSpaceLayerRelMapper;
import com.easy.etl.project.service.IProjectWorkSpaceLayerRelService;
import com.easy.etl.project.vo.ProjectWorkSpaceLayerRelVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 项目工作空间分层关联-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectWorkSpaceLayerRelServiceImpl extends BaseServiceImpl<ProjectWorkSpaceLayerRelMapper, ProjectWorkSpaceLayerRel> implements IProjectWorkSpaceLayerRelService {


    @Override
    public List<ProjectWorkSpaceLayerRelVo> queryProjectWorkSpaceLayer(String projectId) {
        return baseMapper.queryProjectWorkSpaceLayer(projectId);
    }
}
