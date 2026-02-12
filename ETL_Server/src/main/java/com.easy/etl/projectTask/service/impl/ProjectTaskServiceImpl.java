package com.easy.etl.projectTask.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.em.DbEnum;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.datax.enums.DataXEnum;
import com.easy.etl.datax.kit.DataXKit;
import com.easy.etl.datax.model.*;
import com.easy.etl.datax.model.core.Core;
import com.easy.etl.datax.model.core.CoreSpeed;
import com.easy.etl.datax.model.core.DataXServer;
import com.easy.etl.datax.model.reader.ReaderConnection;
import com.easy.etl.datax.model.writer.WriterConnection;
import com.easy.etl.datax.rdbms.RdbmsReaderParameter;
import com.easy.etl.datax.rdbms.RdbmsWriterParameter;
import com.easy.etl.em.TaskTypeEnum;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.mapper.BatchProjectLayerTaskChannelMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.mapper.BatchProjectLayerTaskFieldMapMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.mapper.BatchProjectLayerTaskReadMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.vo.BatchProjectLayerTaskReadVo;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.mapper.BatchProjectLayerTaskWriteMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import com.easy.etl.conf.entity.Conf;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.mapper.ProjectMemberMapper;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskFolderDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskFolder;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.mapper.ProjectTaskFolderMapper;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTask.mapper.ProjectTaskSchedueMapper;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTask.vo.ProjectTaskFolderVo;
import com.easy.etl.projectTask.vo.ProjectTaskTreeVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTask.vo.TaskTreeVo;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskEvn.mapper.ProjectTaskEvnMapper;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.mapper.ProjectTaskParameterMapper;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.mapper.ProjectTaskScriptMapper;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 项目任务-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskServiceImpl extends BaseServiceImpl<ProjectTaskMapper, ProjectTask> implements IProjectTaskService {

    @Autowired
    private ProjectTaskFolderMapper projectTaskFolderMapper;
    @Autowired
    private BatchProjectLayerTaskReadMapper batchProjectLayerTaskReadMapper;

    @Autowired
    private BatchProjectLayerTaskWriteMapper batchProjectLayerTaskWriteMapper;
    @Autowired
    private BatchProjectLayerTaskFieldMapMapper batchProjectLayerTaskFieldMapMapper;
    @Autowired
    private BatchProjectLayerTaskChannelMapper batchProjectLayerTaskChannelMapper;
    @Autowired
    private ProjectMemberMapper projectMemberMapper;

    @Autowired
    private ProjectTaskParameterMapper projectTaskParameterMapper;

    @Autowired
    private ProjectTaskScriptMapper projectTaskScriptMapper;

    @Autowired
    private ProjectTaskSchedueMapper projectTaskSchedueMapper;

    @Autowired
    private ProjectTaskEvnMapper projectTaskEvnMapper;

    @Autowired
    private IDbSourceService dbSourceService;

    @Autowired
    private IConfService confService;

    /**
     * 保存或修改Entity
     *
     * @param projectTask
     */
    @Override
    public boolean saveOrUpdateProjectTask(ProjectTask projectTask) {
        return saveOrUpdate(projectTask);
    }

    @Override
    public boolean saveTask(ProjectTask projectTask, ProjectTaskSchedue taskSchedue, String scriptContent, List<ProjectTaskParameter> projectTaskParameters, String evnContent, int taskStep) {
        projectTaskParameterMapper.delete(new LambdaUpdateWrapper<ProjectTaskParameter>()
                .eq(ProjectTaskParameter::getTaskId, projectTask.getTaskId())
        );

        //修改任务步骤
        ProjectTask task = new ProjectTask();
        task.setTaskStep(taskStep);
        task.setTaskId(projectTask.getTaskId());
        task.setCorn(projectTask.getCorn());
        task.setStartDate(projectTask.getStartDate());
        task.setEndDate(projectTask.getEndDate());
        task.setCornStatus(projectTask.getCornStatus());
        task.setDbSourceId(projectTask.getDbSourceId());
        this.updateById(task);
        //任务参数
        for (ProjectTaskParameter projectTaskParameter : projectTaskParameters) {
            projectTaskParameter.setId(idBulider());
            projectTaskParameter.setTaskId(projectTask.getTaskId());
            projectTaskParameter.setProjectId(projectTask.getProjectId());
            projectTaskParameter.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
            projectTaskParameter.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
            projectTaskParameter.setWorkSpaceType(projectTask.getWorkSpaceType());
            projectTaskParameter.setTaskFolderId(projectTask.getTaskFolderId());
            projectTaskParameterMapper.insert(projectTaskParameter);
        }
        //任务调度方式
        taskSchedue.setTaskId(projectTask.getTaskId());
        taskSchedue.setProjectId(projectTask.getProjectId());
        taskSchedue.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        taskSchedue.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        taskSchedue.setWorkSpaceType(projectTask.getWorkSpaceType());
        taskSchedue.setTaskFolderId(projectTask.getTaskFolderId());
        if (projectTaskSchedueMapper.selectCount(new LambdaUpdateWrapper<ProjectTaskSchedue>().eq(ProjectTaskSchedue::getTaskId, projectTask.getTaskId())) > 0) {
            projectTaskSchedueMapper.updateById(taskSchedue);
        } else {
            projectTaskSchedueMapper.insert(taskSchedue);
        }
        //任务环境变量
        ProjectTaskEvn evn = new ProjectTaskEvn();
        evn.setId(projectTask.getTaskId());
        evn.setTaskId(projectTask.getTaskId());
        evn.setProjectId(projectTask.getProjectId());
        evn.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        evn.setWorkSpaceType(projectTask.getWorkSpaceType());
        evn.setTaskFolderId(projectTask.getTaskFolderId());
        evn.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        evn.setEvnContent(evnContent);
        if (projectTaskEvnMapper.selectCount(new LambdaUpdateWrapper<ProjectTaskEvn>().eq(ProjectTaskEvn::getId, projectTask.getTaskId())) > 0) {
            projectTaskEvnMapper.updateById(evn);
        } else {
            projectTaskEvnMapper.insert(evn);
        }

        //任务脚本
        boolean insert = false;
        ProjectTaskScript projectTaskScript = projectTaskScriptMapper.selectById(projectTask.getTaskId());
        if (projectTaskScript == null) {
            projectTaskScript = new ProjectTaskScript();
            insert = true;
        }
        projectTaskScript.setTaskId(projectTask.getTaskId());
        projectTaskScript.setProjectId(projectTask.getProjectId());
        projectTaskScript.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        projectTaskScript.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        projectTaskScript.setWorkSpaceType(projectTask.getWorkSpaceType());
        projectTaskScript.setTaskFolderId(projectTask.getTaskFolderId());
        switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(projectTask.getWorkSpaceType()))) {
            case SYNC:
                projectTaskScript.setScriptContent(createDataxJson(projectTask.getTaskId(), 1, 0));
                break;
            case SPARK_SQL:
                projectTaskScript.setScriptContent(scriptContent);
                break;
        }
        if (insert) {
            projectTaskScriptMapper.insert(projectTaskScript);
            return true;
        }
        projectTaskScriptMapper.updateById(projectTaskScript);
        return true;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ProjectTaskVo> queryProjectTaskPage(IPage<ProjectTaskVo> page, ProjectTaskDTO param) {
        return page.setRecords(baseMapper.queryProjectTaskPage(page, param));
    }

    @Override
    public List<ProjectTaskVo> queryProjectTaskAll(ProjectTaskDTO param) {
        return baseMapper.queryProjectTaskAll(param);
    }

    @Override
    public TaskTreeVo queryProjectTaskTree(ProjectTaskDTO param, ProjectMemberDTO memberDTO, ProjectTaskFolderDTO taskFolderDTO) {

        TaskTreeVo taskTreeVo = new TaskTreeVo();
        List<ProjectTaskTreeVo> treeVos = new ArrayList<>();

        List<ProjectTaskFolderVo> allTaskFolderVos = projectTaskFolderMapper.queryProjectTaskFolderAll(taskFolderDTO);

        List<ProjectTaskVo> allTask = baseMapper.queryProjectTaskAll(param);
        //任务
        Map<String, List<ProjectTaskTreeVo>> taskMap = new HashMap<>();
        if (param.getShowFolderOnly() == 0) {
            List<ProjectTaskVo> taskVos = baseMapper.queryProjectTaskAll(param);
//            taskTreeVo.setTaskNodes(taskVos);
            taskMap = createTaskMap(taskVos);
        }


        memberDTO.setStaffId(memberDTO.getUserId());
        List<ProjectMemberWorkSpaceVo> projectMemberWorkSpaceVos = projectMemberMapper.queryProjectMemberWorkSpaceLayer(memberDTO);
        for (ProjectMemberWorkSpaceVo projectMemberWorkSpaceVo : projectMemberWorkSpaceVos) {
            ProjectTaskTreeVo treeVo = new ProjectTaskTreeVo();
            treeVo.setId(projectMemberWorkSpaceVo.getProjectWorkSpaceLayerRelId());
            treeVo.setPid("0");
            treeVo.setLeval(1);
            treeVo.setClose(true);
            treeVo.setProjectId(projectMemberWorkSpaceVo.getProjectId());
            treeVo.setProjectWorkSpaceId(projectMemberWorkSpaceVo.getProjectWorkSpaceId());
            treeVo.setProjectWorkSpaceLayerRelId(projectMemberWorkSpaceVo.getProjectWorkSpaceLayerRelId());
            treeVo.setWorkSpaceType(projectMemberWorkSpaceVo.getWorkSpaceType());
            treeVo.setTaskType(projectMemberWorkSpaceVo.getWorkSpaceType());
            treeVo.setName(projectMemberWorkSpaceVo.getWorkSpaceLayerName());

            List<ProjectTaskFolderVo> childerTaskFolderVos = allTaskFolderVos.stream().filter(v -> v.getTaskFolderPid().equals(treeVo.getProjectWorkSpaceLayerRelId())).collect(Collectors.toList());

            treeVo.setChildren(createTree(allTaskFolderVos, childerTaskFolderVos, projectMemberWorkSpaceVo.getProjectWorkSpaceLayerRelId(), taskMap));

            treeVos.add(treeVo);
        }
        taskTreeVo.setTaskTree(treeVos);

        return taskTreeVo;
    }

    private List<ProjectTaskTreeVo> createTree(List<ProjectTaskFolderVo> allTaskFolderVos, List<ProjectTaskFolderVo> childerTaskFolderVos, String pid, Map<String, List<ProjectTaskTreeVo>> taskMap) {
        List<ProjectTaskTreeVo> folderTree = new ArrayList<>();
        for (ProjectTaskFolderVo folderVo : childerTaskFolderVos) {
            ProjectTaskTreeVo treeVo = new ProjectTaskTreeVo();
            treeVo.setId(folderVo.getTaskFolderId());
            treeVo.setPid(pid);
            treeVo.setLeval(2);
            treeVo.setClose(true);
            treeVo.setProjectId(folderVo.getProjectId());
            treeVo.setProjectWorkSpaceId(folderVo.getProjectWorkSpaceId());
            treeVo.setProjectWorkSpaceLayerRelId(folderVo.getProjectWorkSpaceLayerRelId());
            treeVo.setWorkSpaceType(folderVo.getWorkSpaceType());
            treeVo.setTaskType(folderVo.getWorkSpaceType());
            treeVo.setName(folderVo.getFolderName());

            List<ProjectTaskTreeVo> childer = new ArrayList<>();
            List<ProjectTaskFolderVo> childerFolder = allTaskFolderVos.stream().filter(v -> v.getTaskFolderPid().equals(folderVo.getTaskFolderId())).collect(Collectors.toList());
            if (childerFolder != null && !childerFolder.isEmpty()) {
                childer.addAll(createTree(allTaskFolderVos, childerFolder, folderVo.getTaskFolderId(), taskMap));
            }
            if (taskMap.containsKey(folderVo.getTaskFolderId())) {
                childer.addAll(taskMap.get(folderVo.getTaskFolderId()));
            }
            if (childer != null && !childer.isEmpty()) {
                treeVo.setChildren(childer);
            }
            folderTree.add(treeVo);
        }
        return folderTree;
    }


    private Map<String, List<ProjectTaskTreeVo>> createTaskMap(List<ProjectTaskVo> taskVos) {
        Map<String, List<ProjectTaskTreeVo>> taskMapRet = new HashMap<>();
        if (taskVos != null && !taskVos.isEmpty()) {
            Map<String, List<ProjectTaskVo>> group = taskVos.stream().collect(Collectors.groupingBy(ProjectTaskVo::getTaskFolderId, LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
            for (Map.Entry<String, List<ProjectTaskVo>> entry : group.entrySet()) {
                taskMapRet.put(entry.getKey(),
                        entry.getValue().stream().map(v -> {
                            ProjectTaskTreeVo vo = new ProjectTaskTreeVo();
                            vo.setId(v.getTaskId());
                            vo.setPid(v.getTaskFolderId());
                            vo.setName(v.getTaskName());
                            vo.setMenu(true);
                            vo.setClose(true);
                            vo.setLeval(3);
                            vo.setProjectId(v.getProjectId());
                            vo.setProjectId(v.getProjectId());
                            vo.setProjectWorkSpaceId(v.getProjectWorkSpaceId());
                            vo.setProjectWorkSpaceLayerRelId(v.getProjectWorkSpaceLayerRelId());
                            vo.setWorkSpaceType(v.getWorkSpaceType());
                            vo.setTaskType(v.getWorkSpaceType());
                            return vo;
                        }).collect(Collectors.toList())
                );
            }
        }
        return taskMapRet;
    }

    @Override
    public String createDataxJson(String taskId, int submitJob, int parseParams) {
        ProjectTask task = baseMapper.selectById(taskId);
        if (task == null) {
            return null;
        }
        ConfVo conf = confService.getConf();

        BatchProjectLayerTaskReadVo read = batchProjectLayerTaskReadMapper.queryBatchProjectLayerTaskRead(taskId);
        BatchProjectLayerTaskWriteVo write = batchProjectLayerTaskWriteMapper.queryBatchProjectLayerTaskWrite(taskId);


        BatchProjectLayerTaskFieldMapDTO param = new BatchProjectLayerTaskFieldMapDTO();
        param.setTaskId(taskId);
        List<BatchProjectLayerTaskFieldMapVo> columns = batchProjectLayerTaskFieldMapMapper.queryBatchProjectLayerTaskFieldMapAll(param);
        List<ColumnInfo> readColumns = new ArrayList<>();
        List<ColumnInfo> writeColumns = new ArrayList<>();
        for (BatchProjectLayerTaskFieldMapVo vo : columns) {
            readColumns.add(new ColumnInfo(vo.getReadFieldName(),
                    vo.getReadFieldType(),
                    vo.getReadColumnSize(),
                    vo.getReadPoint(),
                    vo.getReadFieldFormat(),
                    null, vo.getShowOrder()));
            writeColumns.add(new ColumnInfo(vo.getWriteFieldName(),
                    vo.getWriteFieldType(),
                    vo.getWriteColumnSize(),
                    vo.getWritePoint(),
                    null,
                    null, vo.getShowOrder()));
        }
        if (submitJob == 0) {
            read.setPassword("*****");
            write.setPassword("****");
        }
        DbEnum readDbEnum = DbEnum.getByDriverClassName(read.getJdbcDrive());
        DbEnum writeDbEnum = DbEnum.getByDriverClassName(write.getJdbcDrive());
        BatchProjectLayerTaskChannel channel = batchProjectLayerTaskChannelMapper.selectOne(new QueryWrapper<BatchProjectLayerTaskChannel>().lambda().eq(BatchProjectLayerTaskChannel::getTaskId, taskId));
        Connection readConnection = new ReaderConnection(readDbEnum, read, readColumns, read.getQueryCondition(), parseParams);
        Connection writeConnection = new WriterConnection(writeDbEnum, write);

        RdbmsReaderParameter readerParameter = new RdbmsReaderParameter(readDbEnum, read, readConnection, readColumns, read.getSplitPk(), read.getFetchSize());
        RdbmsWriterParameter writeParameter = new RdbmsWriterParameter(writeDbEnum, write, writeConnection, writeColumns, write.getPreSql(), write.getPostSql(), write.getWriteMode(), write.getHiveMode());
        Plugin reader = new Plugin(readDbEnum.getReaderName(), readerParameter);
        Plugin writer = new Plugin(writeDbEnum.getWriterName(), writeParameter);

        Speed speed = new Speed(channel.getTaskProcess(), channel.getTaskSpeed());
        ErrorLimit errorLimit = new ErrorLimit(channel.getErrorRecord(), channel.getPercentage().longValue());
        DataXServer dataXServer = null;
        if (submitJob == 1) {
            dataXServer = new DataXServer();
            ;
        }
        Core core = new Core(new CoreSpeed(channel.getTaskSpeed()), dataXServer);
        return DataXKit.createJson(core, speed, errorLimit, reader, writer);
    }

    @Override
    public ProjectTaskVo queryProjectTaskById(String taskId) {
        return baseMapper.queryProjectTaskById(taskId);
    }

}
