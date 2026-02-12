package com.easy.etl.projectWorkSpace.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.project.service.IProjectWorkSpaceLayerRelService;
import com.easy.etl.project.vo.ProjectWorkSpaceLayerRelVo;
import com.easy.etl.projectWorkSpace.dto.ProjectWorkSpaceDTO;
import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;
import com.easy.etl.projectWorkSpace.service.IProjectWorkSpaceService;
import com.easy.etl.projectWorkSpace.vo.ProjectWorkSpaceTreeVo;
import com.easy.etl.projectWorkSpace.vo.ProjectWorkSpaceVo;
import com.easy.etl.projectWorkSpaceLayer.dto.ProjectWorkSpaceLayerDTO;
import com.easy.etl.projectWorkSpaceLayer.service.IProjectWorkSpaceLayerService;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 项目工作空间-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectWorkSpace")
public class ProjectWorkSpaceController extends BaseController {

    @Autowired
    private IProjectWorkSpaceService projectWorkSpaceService;

    @Autowired
    private IProjectWorkSpaceLayerService projectWorkSpaceLayerService;

    @Autowired
    private IProjectWorkSpaceLayerRelService projectWorkSpaceLayerRelService;



    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间及层级-项目配置空间层级使用")
    @GetMapping(value = "/projectWorkSpaceLayerTree")
    public ApiResult projectWorkSpaceLayerTree(@NotNull(message = "项目不能为空") String projectId) {
        ProjectWorkSpaceDTO workSpaceDTO=new ProjectWorkSpaceDTO();
        List<ProjectWorkSpaceVo> workSpaces=projectWorkSpaceService.queryProjectWorkSpaceAll(workSpaceDTO);

        ProjectWorkSpaceLayerDTO workSpaceLayerDTO=new ProjectWorkSpaceLayerDTO();
        List<ProjectWorkSpaceLayerVo> workSpaceLayers=projectWorkSpaceLayerService.queryProjectWorkSpaceLayerAll(workSpaceLayerDTO);
        List<ProjectWorkSpaceTreeVo> treeVos=new ArrayList<>();
        for (ProjectWorkSpaceVo workSpaceVo:workSpaces) {
            String projectWorkSpaceId=SecureUtil.md5(projectId.concat("_")
                    .concat(workSpaceVo.getWorkSpaceId()).concat("_")
                    .concat(workSpaceVo.getWorkSpaceType()));

           List<ProjectWorkSpaceLayerVo> vos= workSpaceLayers.stream().map(v->{
               String id= SecureUtil.md5(projectId.concat("_")
                       .concat(workSpaceVo.getWorkSpaceId()).concat("_")
                       .concat(workSpaceVo.getWorkSpaceType()).concat("_")
                       .concat(v.getWorkSpaceLayerId()));

               ProjectWorkSpaceLayerVo vo=new ProjectWorkSpaceLayerVo();
               vo.setId(id);
               vo.setProjectWorkSpaceId(projectWorkSpaceId);
               vo.setLabel(v.getLabel());
               vo.setName(v.getName());
               vo.setProjectId(projectId);
               vo.setWorkSpaceId(workSpaceVo.getWorkSpaceId());
               vo.setWorkSpaceType(workSpaceVo.getWorkSpaceType());
               vo.setWorkSpaceLayerId(v.getWorkSpaceLayerId());
               vo.setWorkSpaceLayerName(v.getWorkSpaceLayerName());
               return vo;
            }).collect(Collectors.toList());
            ProjectWorkSpaceTreeVo treeVo=new ProjectWorkSpaceTreeVo();
            BeanUtil.copyProperties(workSpaceVo,treeVo);
            treeVo.setId(projectWorkSpaceId);
            treeVo.setProjectWorkSpaceId(projectWorkSpaceId);
            treeVo.setProjectId(projectId);
            treeVo.setChildren(vos);
            treeVos.add(treeVo);
        }
        return ApiResult.ok(treeVos);
    }

    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间及层级-项目人员配置权限使用")
    @GetMapping(value = "/projectWorkSpaceLayerMemberTree")
    public ApiResult projectWorkSpaceLayerMemberTree(@NotNull(message = "项目不能为空") String projectId) {

        List<ProjectWorkSpaceLayerRelVo> list=projectWorkSpaceLayerRelService.queryProjectWorkSpaceLayer(projectId);

        List<ProjectWorkSpaceTreeVo> treeVos=new ArrayList<>();
        Map<String,List<ProjectWorkSpaceLayerRelVo>> group=
                list.stream().collect(Collectors.groupingBy(ProjectWorkSpaceLayerRelVo::getProjectWorkSpaceId
                        ,LinkedHashMap::new,
                        Collectors.toList()));
        for (Map.Entry<String,List<ProjectWorkSpaceLayerRelVo>> entry : group.entrySet()) {
            ProjectWorkSpaceLayerRelVo relVo=entry.getValue().get(0);
            ProjectWorkSpaceTreeVo treeVo=new ProjectWorkSpaceTreeVo();
            treeVo.setId(relVo.getProjectWorkSpaceId());
            treeVo.setProjectWorkSpaceId(relVo.getProjectWorkSpaceId());
            treeVo.setProjectId(projectId);
            treeVo.setWorkSpaceName(relVo.getWorkSpaceName());
            treeVo.setLabel(relVo.getWorkSpaceName());
            treeVo.setName(relVo.getWorkSpaceName());
            treeVo.setWorkSpaceId(relVo.getWorkSpaceId());
            treeVo.setWorkSpaceType(relVo.getWorkSpaceType());
            treeVo.setProjectId(projectId);

            List<ProjectWorkSpaceLayerVo> vos= entry.getValue().stream().map(v->{

                ProjectWorkSpaceLayerVo vo=new ProjectWorkSpaceLayerVo();
                vo.setId(v.getProjectWorkSpaceLayerRelId());
                vo.setLabel(v.getWorkSpaceLayerName());
                vo.setName(v.getWorkSpaceLayerName());
                vo.setProjectId(projectId);
                vo.setWorkSpaceId(treeVo.getWorkSpaceId());
                vo.setWorkSpaceType(treeVo.getWorkSpaceType());
                vo.setWorkSpaceLayerId(v.getWorkSpaceLayerId());
                vo.setWorkSpaceLayerName(v.getWorkSpaceLayerName());
                vo.setProjectWorkSpaceId(treeVo.getProjectWorkSpaceId());
                return vo;
            }).collect(Collectors.toList());
            treeVo.setChildren(vos);
            treeVos.add(treeVo);
        }
        return ApiResult.ok(treeVos);
    }


    /**
     * 保存或修改
     *
     * @param projectWorkSpace
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    @SaCheckPermission(value = {"admin:etl:projectWorkSpace:add", "admin:etl:projectWorkSpace:edit"})
    public ApiResult saveOrUpdate(@Validated ProjectWorkSpace projectWorkSpace) {
        if (projectWorkSpaceService.saveOrUpdateProjectWorkSpace(projectWorkSpace)) {
            return ApiResult.ok(projectWorkSpace);
        }
        return ApiResult.fail("更新失败");
    }


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间-分页查询")
    @GetMapping(value = "/queryAll")
    public ApiResult<List<ProjectWorkSpaceVo>> queryAll(ProjectWorkSpaceDTO param) {
        List<ProjectWorkSpaceVo> list = projectWorkSpaceService.queryProjectWorkSpaceAll(param);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @param workSpaceId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:projectWorkSpace:getById"})
    public ApiResult<ProjectWorkSpace> getById(@NotNull(message = "主键不能为空") String workSpaceId) {
        return ApiResult.ok(projectWorkSpaceService.getById(workSpaceId));
    }

    /**
     * 根据主键删除
     *
     * @param workSpaceId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作空间-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:projectWorkSpace:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String workSpaceId) {
        projectWorkSpaceService.removeById(workSpaceId);
        return ApiResult.ok();
    }
}
