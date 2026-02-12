package com.easy.etl.backup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.backup.dto.ProjectTaskBackupDTO;
import com.easy.etl.backup.entity.*;
import com.easy.etl.backup.mapper.*;
import com.easy.etl.backup.service.ITaskBackupService;
import com.easy.etl.backup.vo.ProjectTaskBackupVo;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.mapper.BatchProjectLayerTaskChannelMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.mapper.BatchProjectLayerTaskFieldMapMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.mapper.BatchProjectLayerTaskReadMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.mapper.BatchProjectLayerTaskReadFieldMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.mapper.BatchProjectLayerTaskWriteMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.mapper.BatchProjectLayerTaskWriteFieldMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.vo.BatchProjectLayerTaskWriteFieldVo;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.mapper.ProjectTaskLazyMapper;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTask.mapper.ProjectTaskSchedueMapper;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskEvn.mapper.ProjectTaskEvnMapper;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.mapper.ProjectTaskFlowMapper;
import com.easy.etl.projectTaskFlow.mapper.ProjectTaskFlowNodeMapper;
import com.easy.etl.projectTaskFlow.mapper.ProjectTaskFlowSourceTargetNodeMapper;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.mapper.ProjectTaskParameterMapper;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.mapper.ProjectTaskScriptMapper;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目任务备份-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class TaskBackupServiceImpl implements ITaskBackupService {


    @Autowired
    private ProjectTaskMapper projectTaskMapper;
    @Autowired
    private ProjectTaskBackupMapper projectTaskBackupMapper;

    @Autowired
    private ProjectTaskScriptMapper projectTaskScriptMapper;
    @Autowired
    private ProjectTaskScriptBackupMapper projectTaskScriptBackupMapper;

    @Autowired
    private ProjectTaskEvnMapper projectTaskEvnMapper;
    @Autowired
    private ProjectTaskEvnBackupMapper projectTaskEvnBackupMapper;

    @Autowired
    private ProjectTaskParameterMapper projectTaskParameterMapper;
    @Autowired
    private ProjectTaskParameterBackupMapper projectTaskParameterBackupMapper;

    @Autowired
    private ProjectTaskLazyMapper projectTaskLazyMapper;
    @Autowired
    private ProjectTaskLazyBackupMapper projectTaskLazyBackupMapper;

    @Autowired
    private ProjectTaskSchedueMapper projectTaskSchedueMapper;
    @Autowired
    private ProjectTaskSchedueBackupMapper projectTaskSchedueBackupMapper;

    @Autowired
    private ProjectTaskFlowMapper projectTaskFlowMapper;
    @Autowired
    private ProjectTaskFlowBackupMapper projectTaskFlowBackupMapper;

    @Autowired
    private ProjectTaskFlowNodeMapper projectTaskFlowNodeMapper;
    @Autowired
    private ProjectTaskFlowNodeBackupMapper projectTaskFlowNodeBackupMapper;

    @Autowired
    private ProjectTaskFlowSourceTargetNodeMapper projectTaskFlowSourceTargetNodeMapper;
    @Autowired
    private ProjectTaskFlowSourceTargetNodeBackupMapper projectTaskFlowSourceTargetNodeBackupMapper;

    @Autowired
    private BatchProjectLayerTaskReadMapper batchProjectLayerTaskReadMapper;
    @Autowired
    private BatchProjectLayerTaskReadBackupMapper batchProjectLayerTaskReadBackupMapper;

    @Autowired
    private BatchProjectLayerTaskWriteMapper batchProjectLayerTaskWriteMapper;
    @Autowired
    private BatchProjectLayerTaskWriteBackupMapper batchProjectLayerTaskWriteBackupMapper;


    @Autowired
    private BatchProjectLayerTaskFieldMapMapper batchProjectLayerTaskFieldMapMapper;
    @Autowired
    private BatchProjectLayerTaskFieldMapBackupMapper batchProjectLayerTaskFieldMapBackupMapper;

    @Autowired
    private BatchProjectLayerTaskChannelMapper batchProjectLayerTaskChannelMapper;
    @Autowired
    private BatchProjectLayerTaskChannelBackupMapper batchProjectLayerTaskChannelBackupMapper;

    @Autowired
    private BatchProjectLayerTaskReadFieldMapper batchProjectLayerTaskReadFieldMapper;
    @Autowired
    private BatchProjectLayerTaskReadFieldBackupMapper batchProjectLayerTaskReadFieldBackupMapper;

    @Autowired
    private BatchProjectLayerTaskWriteFieldMapper batchProjectLayerTaskWriteFieldMapper;
    @Autowired
    private BatchProjectLayerTaskWriteFieldBackupMapper batchProjectLayerTaskWriteFieldBackupMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean rollBack(String taskId) {
        //项目任务备份
        ProjectTaskBackup projectTaskBackup = projectTaskBackupMapper.selectById(taskId);
        if (ObjectUtils.isEmpty(projectTaskBackup)) {
            return false;
        }
        ProjectTask projectTask = new ProjectTask();
        BeanUtils.copyProperties(projectTaskBackup, projectTask);
        projectTask.setTaskId(projectTaskBackup.getBackupTaskId());
        projectTaskMapper.updateById(projectTask);

        //任务脚本备份
        ProjectTaskScriptBackup projectTaskScriptBackup = projectTaskScriptBackupMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskScriptBackup)) {
            ProjectTaskScript projectTaskScript = new ProjectTaskScript();
            BeanUtils.copyProperties(projectTaskScriptBackup, projectTaskScript);
            projectTaskScript.setTaskId(projectTaskScriptBackup.getBackupTaskId());
            projectTaskScriptMapper.updateById(projectTaskScript);
        }
        //任务环境变量备份
        List<ProjectTaskEvnBackup> projectTaskEvnBackups = projectTaskEvnBackupMapper.selectList(
                new LambdaUpdateWrapper<ProjectTaskEvnBackup>()
                        .eq(ProjectTaskEvnBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(projectTaskEvnBackups)) {
            List<ProjectTaskEvn> projectTaskEvns = projectTaskEvnBackups.stream().map(v -> {
                ProjectTaskEvn projectTaskEvn = new ProjectTaskEvn();
                BeanUtils.copyProperties(v, projectTaskEvn);
                projectTaskEvn.setTaskId(v.getBackupTaskId());
                projectTaskEvn.setId(v.getBackupId());
                return projectTaskEvn;
            }).collect(Collectors.toList());
            for (ProjectTaskEvn backUp : projectTaskEvns) {
                if (ObjectUtils.isEmpty(projectTaskEvnMapper.selectById(backUp.getId()))) {
                    projectTaskEvnMapper.insert(backUp);
                    continue;
                }
                projectTaskEvnMapper.updateById(backUp);
            }
        }

        //任务参数备份还原
        List<ProjectTaskParameterBackup> projectTaskParameterBackups = projectTaskParameterBackupMapper.selectList(
                new LambdaUpdateWrapper<ProjectTaskParameterBackup>()
                        .eq(ProjectTaskParameterBackup::getTaskId, taskId)
        );

        if (!ObjectUtils.isEmpty(projectTaskParameterBackups)) {
            List<ProjectTaskParameter> projectTaskParameters = projectTaskParameterBackups.stream().map(v -> {
                ProjectTaskParameter projectTaskParameter = new ProjectTaskParameter();
                BeanUtils.copyProperties(v, projectTaskParameter);
                projectTaskParameter.setId(v.getBackupId());
                projectTaskParameter.setTaskId(v.getBackupTaskId());
                return projectTaskParameter;
            }).collect(Collectors.toList());
            projectTaskParameterMapper.delete(new LambdaUpdateWrapper<ProjectTaskParameter>()
                    .in(ProjectTaskParameter::getTaskId, projectTaskParameters.stream().map(v->v.getTaskId()).collect(Collectors.toList())));
            for (ProjectTaskParameter p:projectTaskParameters) {
                projectTaskParameterMapper.insert( p);
            }
        }

        //项目任务依懒备份还原
        List<ProjectTaskLazyBackup> projectTaskLazyBackups = projectTaskLazyBackupMapper.selectList(
                new LambdaUpdateWrapper<ProjectTaskLazyBackup>()
                        .eq(ProjectTaskLazyBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(projectTaskLazyBackups)) {
            List<ProjectTaskLazy> projectTaskEvns = projectTaskLazyBackups.stream().map(v -> {
                ProjectTaskLazy projectTaskLazy = new ProjectTaskLazy();
                BeanUtils.copyProperties(v, projectTaskLazy);
                projectTaskLazy.setTaskLazyId(v.getBackupTaskLazyId());
                projectTaskLazy.setTaskId(v.getBackupTaskId());
                projectTaskLazy.setLazyTaskId(v.getBackupLazyTaskId());
                return projectTaskLazy;
            }).collect(Collectors.toList());
            projectTaskLazyMapper.delete(new LambdaUpdateWrapper<ProjectTaskLazy>()
                    .in(ProjectTaskLazy::getTaskId, projectTaskEvns.stream().map(v -> v.getTaskId()).collect(Collectors.toList()))
            );
            for (ProjectTaskLazy backUp : projectTaskEvns) {
                projectTaskLazyMapper.insert(backUp);
            }
        }
        //项目任务定时备份
        ProjectTaskSchedueBackup projectTaskSchedueBackup = projectTaskSchedueBackupMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskSchedueBackup)) {
            ProjectTaskSchedue projectTaskSchedue = new ProjectTaskSchedue();
            BeanUtils.copyProperties(projectTaskSchedueBackup, projectTaskSchedue);
            projectTaskSchedue.setTaskId(projectTaskSchedueBackup.getBackupTaskId());
            projectTaskSchedueMapper.updateById(projectTaskSchedue);
        }
        //项目工作流备份
        ProjectTaskFlowBackup projectTaskFlowBackup = projectTaskFlowBackupMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskFlowBackup)) {
            ProjectTaskFlow projectTaskFlow = new ProjectTaskFlow();
            BeanUtils.copyProperties(projectTaskFlowBackup, projectTaskFlow);
            projectTaskFlow.setTaskId(projectTaskFlowBackup.getBackupTaskId());
            projectTaskFlowMapper.updateById(projectTaskFlow);
        }
        //项目工作流节点
        List<ProjectTaskFlowNodeBackup> projectTaskFlowNodeBackups = projectTaskFlowNodeBackupMapper.selectList(
                new LambdaUpdateWrapper<ProjectTaskFlowNodeBackup>()
                        .eq(ProjectTaskFlowNodeBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(projectTaskFlowNodeBackups)) {
            List<ProjectTaskFlowNode> projectTaskFlowNodes = projectTaskFlowNodeBackups.stream().map(v -> {
                ProjectTaskFlowNode projectTaskFlowNode = new ProjectTaskFlowNode();
                BeanUtils.copyProperties(v, projectTaskFlowNode);
                projectTaskFlowNode.setNodeId(v.getBackupNodeId());
                projectTaskFlowNode.setTaskId(v.getBackupTaskId());
                return projectTaskFlowNode;
            }).collect(Collectors.toList());
            projectTaskFlowNodeMapper.delete(new LambdaUpdateWrapper<ProjectTaskFlowNode>()
                    .in(ProjectTaskFlowNode::getTaskId, projectTaskFlowNodes.stream().map(v -> v.getTaskId()).collect(Collectors.toList()))
            );
            for (ProjectTaskFlowNode backUp : projectTaskFlowNodes) {
                projectTaskFlowNodeMapper.insert(backUp);
            }
        }
        //项目工作流节点关系
        List<ProjectTaskFlowSourceTargetNodeBackup> projectTaskFlowSourceTargetNodeBackups = projectTaskFlowSourceTargetNodeBackupMapper.selectList(
                new LambdaUpdateWrapper<ProjectTaskFlowSourceTargetNodeBackup>()
                        .eq(ProjectTaskFlowSourceTargetNodeBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(projectTaskFlowSourceTargetNodeBackups)) {
            List<ProjectTaskFlowSourceTargetNodeBackup> ProjectTaskFlowSourceTargetNodes = projectTaskFlowSourceTargetNodeBackups.stream().map(v -> {
                ProjectTaskFlowSourceTargetNodeBackup projectTaskFlowSourceTargetNode = new ProjectTaskFlowSourceTargetNodeBackup();
                BeanUtils.copyProperties(v, projectTaskFlowSourceTargetNode);
                projectTaskFlowSourceTargetNode.setId(v.getBackupId());
                projectTaskFlowSourceTargetNode.setTaskId(v.getBackupTaskId());
                projectTaskFlowSourceTargetNode.setSourceNodeId(v.getBakeSourceNodeId());
                projectTaskFlowSourceTargetNode.setTargetNodeId(v.getBakeTargetNodeId());
                return projectTaskFlowSourceTargetNode;
            }).collect(Collectors.toList());
            projectTaskFlowSourceTargetNodeMapper.delete(new LambdaUpdateWrapper<ProjectTaskFlowSourceTargetNode>()
                    .in(ProjectTaskFlowSourceTargetNode::getTaskId, ProjectTaskFlowSourceTargetNodes.stream().map(v -> v.getTaskId()).collect(Collectors.toList())));
            for (ProjectTaskFlowSourceTargetNode projectTaskFlowSourceTargetNode : ProjectTaskFlowSourceTargetNodes) {
                projectTaskFlowSourceTargetNodeMapper.insert(projectTaskFlowSourceTargetNode);
            }
        }

        //批处理分层任务来源备份
        List<BatchProjectLayerTaskReadBackup> batchProjectLayerTaskReadBackups = batchProjectLayerTaskReadBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskReadBackup>()
                        .eq(BatchProjectLayerTaskReadBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskReadBackups)) {
            List<BatchProjectLayerTaskRead> batchProjectLayerTaskReads = batchProjectLayerTaskReadBackups.stream().map(v -> {
                BatchProjectLayerTaskRead batchProjectLayerTaskRead = new BatchProjectLayerTaskRead();
                BeanUtils.copyProperties(v, batchProjectLayerTaskRead);
                batchProjectLayerTaskRead.setReadId(v.getBackupReadId());
                batchProjectLayerTaskRead.setTaskId(v.getBackupTaskId());
                return batchProjectLayerTaskRead;
            }).collect(Collectors.toList());

            batchProjectLayerTaskReadMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskRead>()
                    .in(BatchProjectLayerTaskRead::getTaskId, batchProjectLayerTaskReads.stream().map(v -> v.getTaskId()).distinct().collect(Collectors.toList())));
            for (BatchProjectLayerTaskRead backUp : batchProjectLayerTaskReads) {
                batchProjectLayerTaskReadMapper.insert(backUp);
            }
        }
        //批处理分层任务写入备份
        List<BatchProjectLayerTaskWriteBackup> batchProjectLayerTaskWriteBackups = batchProjectLayerTaskWriteBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskWriteBackup>()
                        .eq(BatchProjectLayerTaskWriteBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskWriteBackups)) {
            List<BatchProjectLayerTaskWrite> batchProjectLayerTaskWrites = batchProjectLayerTaskWriteBackups.stream().map(v -> {
                BatchProjectLayerTaskWrite batchProjectLayerTaskWrite = new BatchProjectLayerTaskWrite();
                BeanUtils.copyProperties(v, batchProjectLayerTaskWrite);
                batchProjectLayerTaskWrite.setWriteId(v.getBackupWriteId());
                batchProjectLayerTaskWrite.setTaskId(v.getBackupTaskId());
                return batchProjectLayerTaskWrite;
            }).collect(Collectors.toList());
            batchProjectLayerTaskWriteMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskWrite>()
                    .in(BatchProjectLayerTaskWrite::getTaskId, batchProjectLayerTaskWrites.stream().map(v -> v.getTaskId()).distinct().collect(Collectors.toList())));
            for (BatchProjectLayerTaskWrite backUp : batchProjectLayerTaskWrites) {
                batchProjectLayerTaskWriteMapper.insert(backUp);
            }
        }
        //批处理分层字段映射备份
        List<BatchProjectLayerTaskFieldMapBackup> batchProjectLayerTaskFieldMapBackups = batchProjectLayerTaskFieldMapBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskFieldMapBackup>()
                        .eq(BatchProjectLayerTaskFieldMapBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskFieldMapBackups)) {
            List<BatchProjectLayerTaskFieldMap> batchProjectLayerTaskFieldMaps = batchProjectLayerTaskFieldMapBackups.stream().map(v -> {
                BatchProjectLayerTaskFieldMap batchProjectLayerTaskFieldMap = new BatchProjectLayerTaskFieldMap();
                BeanUtils.copyProperties(v, batchProjectLayerTaskFieldMap);
                batchProjectLayerTaskFieldMap.setId(v.getBackupId());
                batchProjectLayerTaskFieldMap.setTaskId(v.getBackupTaskId());
                batchProjectLayerTaskFieldMap.setReadId(v.getBackupReadId());
                batchProjectLayerTaskFieldMap.setWriteId(v.getBackupWriteId());
                batchProjectLayerTaskFieldMap.setReadFieldId(v.getBackupReadFieldId());
                batchProjectLayerTaskFieldMap.setWriteFieldId(v.getBackupWriteFieldId());
                return batchProjectLayerTaskFieldMap;
            }).collect(Collectors.toList());
            batchProjectLayerTaskFieldMapMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskFieldMap>()
                    .in(BatchProjectLayerTaskFieldMap::getTaskId, batchProjectLayerTaskFieldMaps.stream().map(v -> v.getTaskId()).distinct().collect(Collectors.toList())));
            for (BatchProjectLayerTaskFieldMap backUp : batchProjectLayerTaskFieldMaps) {
                batchProjectLayerTaskFieldMapMapper.insert(backUp);
            }
        }
        //通道控制备份
        List<BatchProjectLayerTaskChannelBackup> batchProjectLayerTaskChannelBackups = batchProjectLayerTaskChannelBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskChannelBackup>()
                        .eq(BatchProjectLayerTaskChannelBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskChannelBackups)) {
            List<BatchProjectLayerTaskChannel> batchProjectLayerTaskChannels = batchProjectLayerTaskChannelBackups.stream().map(v -> {
                BatchProjectLayerTaskChannel batchProjectLayerTaskChannel = new BatchProjectLayerTaskChannel();
                BeanUtils.copyProperties(v, batchProjectLayerTaskChannel);
                batchProjectLayerTaskChannel.setChannelId(v.getBackupChannelId());
                batchProjectLayerTaskChannel.setTaskId(v.getBackupTaskId());
                return batchProjectLayerTaskChannel;
            }).collect(Collectors.toList());
            for (BatchProjectLayerTaskChannel backUp : batchProjectLayerTaskChannels) {
                if (ObjectUtils.isEmpty(batchProjectLayerTaskChannelMapper.selectById(backUp.getChannelId()))) {
                    batchProjectLayerTaskChannelMapper.insert(backUp);
                    continue;
                }
                batchProjectLayerTaskChannelMapper.updateById(backUp);
            }
        }
        //批处理分层任务来源字段备份
        List<BatchProjectLayerTaskReadFieldBackup> batchProjectLayerTaskReadFieldBackups = batchProjectLayerTaskReadFieldBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskReadFieldBackup>()
                        .eq(BatchProjectLayerTaskReadFieldBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskReadFieldBackups)) {
            List<BatchProjectLayerTaskReadField> batchProjectLayerTaskReadFields = batchProjectLayerTaskReadFieldBackups.stream().map(v -> {
                BatchProjectLayerTaskReadField batchProjectLayerTaskReadField = new BatchProjectLayerTaskReadField();
                BeanUtils.copyProperties(v, batchProjectLayerTaskReadField);
                batchProjectLayerTaskReadField.setReadFieldId(v.getBackupReadFieldId());
                batchProjectLayerTaskReadField.setReadId(v.getBackupReadId());
                batchProjectLayerTaskReadField.setTaskId(v.getBackupTaskId());
                return batchProjectLayerTaskReadField;
            }).collect(Collectors.toList());

            batchProjectLayerTaskReadFieldMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskReadField>()
                    .in(BatchProjectLayerTaskReadField::getTaskId, batchProjectLayerTaskReadFields.stream().map(v -> v.getTaskId()).distinct().collect(Collectors.toList())));
            for (BatchProjectLayerTaskReadField backUp : batchProjectLayerTaskReadFields) {
                batchProjectLayerTaskReadFieldMapper.insert(backUp);
            }
        }
        //批处理分层任务写入字段备份
        List<BatchProjectLayerTaskWriteFieldBackup> batchProjectLayerTaskWriteFieldBackups = batchProjectLayerTaskWriteFieldBackupMapper.selectList(
                new LambdaUpdateWrapper<BatchProjectLayerTaskWriteFieldBackup>()
                        .eq(BatchProjectLayerTaskWriteFieldBackup::getTaskId, taskId)
        );
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskWriteFieldBackups)) {
            List<BatchProjectLayerTaskWriteField> batchProjectLayerTaskWriteFields = batchProjectLayerTaskWriteFieldBackups.stream().map(v -> {
                BatchProjectLayerTaskWriteField batchProjectLayerTaskWriteField = new BatchProjectLayerTaskWriteField();
                BeanUtils.copyProperties(v, batchProjectLayerTaskWriteField);
                batchProjectLayerTaskWriteField.setWriteFieldId(v.getBackupWriteFieldId());
                batchProjectLayerTaskWriteField.setWriteId(v.getBackupWriteId());
                batchProjectLayerTaskWriteField.setTaskId(v.getBackupTaskId());
                return batchProjectLayerTaskWriteField;
            }).collect(Collectors.toList());

            batchProjectLayerTaskWriteFieldMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskWriteField>()
                    .in(BatchProjectLayerTaskWriteField::getTaskId, batchProjectLayerTaskWriteFields.stream().map(v -> v.getTaskId()).distinct().collect(Collectors.toList())));

            for (BatchProjectLayerTaskWriteField backUp : batchProjectLayerTaskWriteFields) {
                batchProjectLayerTaskWriteFieldMapper.insert(backUp);
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void taskBackup(String taskId, String remarks) {
        deleteLastTaskBackup(taskId);
        String backupTaskId = SecureUtil.md5(taskId + IdBulider.id());
        //项目任务
        ProjectTask projectTask = projectTaskMapper.queryProjectTaskById(taskId);
        if (!ObjectUtils.isEmpty(projectTask)) {
            ProjectTaskBackup projectTaskBackup = new ProjectTaskBackup();
            BeanUtils.copyProperties(projectTask, projectTaskBackup);
            projectTaskBackup.setBackupTaskId(projectTask.getTaskId());
            projectTaskBackup.setTaskId(backupTaskId);
            projectTaskBackup.setRemark(remarks);
            projectTaskBackup.setAddTime(LocalDateTime.now());
            projectTaskBackup.setUpdateTime(LocalDateTime.now());
            projectTaskBackupMapper.insert(projectTaskBackup);
        }
        //任务脚本备份
        ProjectTaskScript projectTaskScript = projectTaskScriptMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskScript)) {
            ProjectTaskScriptBackup projectTaskScriptBackup = new ProjectTaskScriptBackup();
            BeanUtils.copyProperties(projectTaskScript, projectTaskScriptBackup);
            projectTaskScriptBackup.setBackupTaskId(projectTask.getTaskId());
            projectTaskScriptBackup.setTaskId(backupTaskId);
            projectTaskScriptBackup.setAddTime(LocalDateTime.now());
            projectTaskScriptBackup.setUpdateTime(LocalDateTime.now());
            projectTaskScriptBackupMapper.insert(projectTaskScriptBackup);
        }
        //任务环境变量变量
        List<ProjectTaskEvn> projectTaskEvns = projectTaskEvnMapper.selectList(new LambdaUpdateWrapper<ProjectTaskEvn>().eq(ProjectTaskEvn::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(projectTaskEvns)) {
            List<ProjectTaskEvnBackup> projectTaskEvnBackups = projectTaskEvns.stream().map(projectTaskEvn -> {
                ProjectTaskEvnBackup projectTaskEvnBackup = new ProjectTaskEvnBackup();
                BeanUtils.copyProperties(projectTaskEvn, projectTaskEvnBackup);
                projectTaskEvnBackup.setId(null);
                projectTaskEvnBackup.setBackupId(projectTaskEvn.getId());
                projectTaskEvnBackup.setTaskId(backupTaskId);
                projectTaskEvnBackup.setBackupTaskId(taskId);
                projectTaskEvnBackup.setAddTime(LocalDateTime.now());
                projectTaskEvnBackup.setUpdateTime(LocalDateTime.now());
                return projectTaskEvnBackup;
            }).collect(Collectors.toList());
            for (ProjectTaskEvnBackup backUp : projectTaskEvnBackups) {
                projectTaskEvnBackupMapper.insert(backUp);
            }
        }
        //任务参数
        List<ProjectTaskParameter> projectTaskParameters = projectTaskParameterMapper.selectList(new LambdaUpdateWrapper<ProjectTaskParameter>().eq(ProjectTaskParameter::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(projectTaskParameters)) {
            List<ProjectTaskParameterBackup> projectTaskParameterBackups = projectTaskParameters.stream().map(projectTaskParameter -> {
                ProjectTaskParameterBackup projectTaskParameterBackup = new ProjectTaskParameterBackup();
                BeanUtils.copyProperties(projectTaskParameter, projectTaskParameterBackup);
                projectTaskParameterBackup.setId(null);
                projectTaskParameterBackup.setBackupId(projectTaskParameter.getId());
                projectTaskParameterBackup.setTaskId(backupTaskId);
                projectTaskParameterBackup.setBackupTaskId(taskId);
                projectTaskParameterBackup.setAddTime(LocalDateTime.now());
                projectTaskParameterBackup.setUpdateTime(LocalDateTime.now());
                return projectTaskParameterBackup;
            }).collect(Collectors.toList());
            for (ProjectTaskParameterBackup backUp : projectTaskParameterBackups) {
                projectTaskParameterBackupMapper.insert(backUp);
            }
        }
        //项目任务依懒备份
        List<ProjectTaskLazy> projectTaskLazys = projectTaskLazyMapper.selectList(new LambdaUpdateWrapper<ProjectTaskLazy>().eq(ProjectTaskLazy::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(projectTaskLazys)) {
            List<ProjectTaskLazyBackup> projectTaskLazyBackups = projectTaskLazys.stream().map(projectTaskLazy -> {
                ProjectTaskLazyBackup projectTaskLazyBackup = new ProjectTaskLazyBackup();
                BeanUtils.copyProperties(projectTaskLazy, projectTaskLazyBackup);
                projectTaskLazyBackup.setTaskLazyId(null);
                projectTaskLazyBackup.setTaskId(backupTaskId);
                projectTaskLazyBackup.setBackupTaskId(taskId);
                projectTaskLazyBackup.setBackupLazyTaskId(projectTaskLazy.getLazyTaskId());
                projectTaskLazyBackup.setBackupTaskLazyId(projectTaskLazy.getTaskLazyId());

                projectTaskLazyBackup.setAddTime(LocalDateTime.now());
                return projectTaskLazyBackup;
            }).collect(Collectors.toList());
            for (ProjectTaskLazyBackup backUp : projectTaskLazyBackups) {
                projectTaskLazyBackupMapper.insert(backUp);
            }
        }
        //项目任务定时
        ProjectTaskSchedue projectTaskSchedue = projectTaskSchedueMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskSchedue)) {
            ProjectTaskSchedueBackup projectTaskSchedueBackup = new ProjectTaskSchedueBackup();
            BeanUtils.copyProperties(projectTaskSchedue, projectTaskSchedueBackup);
            projectTaskSchedueBackup.setTaskId(backupTaskId);
            projectTaskSchedueBackup.setBackupTaskId(taskId);
            projectTaskSchedueBackupMapper.insert(projectTaskSchedueBackup);
        }
        //项目工作流备份
        ProjectTaskFlow projectTaskFlow = projectTaskFlowMapper.selectById(taskId);
        if (!ObjectUtils.isEmpty(projectTaskFlow)) {
            ProjectTaskFlowBackup projectTaskFlowBackup = new ProjectTaskFlowBackup();
            BeanUtils.copyProperties(projectTaskFlow, projectTaskFlowBackup);
            projectTaskFlowBackup.setTaskId(backupTaskId);
            projectTaskFlowBackup.setBackupTaskId(taskId);
            projectTaskFlowBackup.setAddTime(LocalDateTime.now());
            projectTaskFlowBackup.setUpdateTime(LocalDateTime.now());
            projectTaskFlowBackupMapper.insert(projectTaskFlowBackup);
        }
        //项目工作流节点备份
        List<ProjectTaskFlowNode> projectTaskFlowNodes = projectTaskFlowNodeMapper.selectList(new LambdaUpdateWrapper<ProjectTaskFlowNode>().eq(ProjectTaskFlowNode::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(projectTaskFlowNodes)) {
            List<ProjectTaskFlowNodeBackup> projectTaskFlowNodeBackups = projectTaskFlowNodes.stream().map(projectTaskFlowNode -> {
                ProjectTaskFlowNodeBackup projectTaskFlowNodeBackup = new ProjectTaskFlowNodeBackup();
                BeanUtils.copyProperties(projectTaskFlowNode, projectTaskFlowNodeBackup);
                projectTaskFlowNodeBackup.setNodeId(null);
                projectTaskFlowNodeBackup.setBackupNodeId(projectTaskFlowNode.getNodeId());
                projectTaskFlowNodeBackup.setTaskId(backupTaskId);
                projectTaskFlowNodeBackup.setBackupTaskId(taskId);
                projectTaskFlowNodeBackup.setBackupExecuteTaskId(projectTaskFlowNode.getExecuteTaskId());
                return projectTaskFlowNodeBackup;
            }).collect(Collectors.toList());
            for (ProjectTaskFlowNodeBackup backUp : projectTaskFlowNodeBackups) {
                projectTaskFlowNodeBackupMapper.insert(backUp);
            }
        }
        //项目工作流节点关系备份
        List<ProjectTaskFlowSourceTargetNode> projectTaskFlowSourceTargetNodes = projectTaskFlowSourceTargetNodeMapper.selectList(new LambdaUpdateWrapper<ProjectTaskFlowSourceTargetNode>().eq(ProjectTaskFlowSourceTargetNode::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(projectTaskFlowSourceTargetNodes)) {
            List<ProjectTaskFlowSourceTargetNodeBackup> projectTaskFlowSourceTargetNodeBackups = projectTaskFlowSourceTargetNodes.stream().map(projectTaskFlowSourceTargetNode -> {
                ProjectTaskFlowSourceTargetNodeBackup projectTaskFlowSourceTargetNodeBackup = new ProjectTaskFlowSourceTargetNodeBackup();
                BeanUtils.copyProperties(projectTaskFlowSourceTargetNode, projectTaskFlowSourceTargetNodeBackup);
                projectTaskFlowSourceTargetNodeBackup.setId(null);
                projectTaskFlowSourceTargetNodeBackup.setBackupId(projectTaskFlowSourceTargetNode.getId());
                projectTaskFlowSourceTargetNodeBackup.setTaskId(backupTaskId);
                projectTaskFlowSourceTargetNodeBackup.setBackupTaskId(taskId);
                projectTaskFlowSourceTargetNodeBackup.setBakeSourceNodeId(projectTaskFlowSourceTargetNode.getSourceNodeId());
                projectTaskFlowSourceTargetNodeBackup.setBakeTargetNodeId(projectTaskFlowSourceTargetNode.getTargetNodeId());
                return projectTaskFlowSourceTargetNodeBackup;
            }).collect(Collectors.toList());
            for (ProjectTaskFlowSourceTargetNodeBackup backUp : projectTaskFlowSourceTargetNodeBackups) {
                projectTaskFlowSourceTargetNodeBackupMapper.insert(backUp);
            }
        }
        //批处理分层任务来源备份
        List<BatchProjectLayerTaskRead> batchProjectLayerTaskReads = batchProjectLayerTaskReadMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskRead>().eq(BatchProjectLayerTaskRead::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskReads)) {
            List<BatchProjectLayerTaskReadBackup> batchProjectLayerTaskReadBackups = batchProjectLayerTaskReads.stream().map(batchProjectLayerTaskRead -> {
                BatchProjectLayerTaskReadBackup batchProjectLayerTaskReadBackup = new BatchProjectLayerTaskReadBackup();
                BeanUtils.copyProperties(batchProjectLayerTaskRead, batchProjectLayerTaskReadBackup);
                batchProjectLayerTaskReadBackup.setTaskId(backupTaskId);
                batchProjectLayerTaskReadBackup.setBackupTaskId(taskId);
                batchProjectLayerTaskReadBackup.setReadId(SecureUtil.md5(backupTaskId + batchProjectLayerTaskRead.getReadId()));
                batchProjectLayerTaskReadBackup.setBackupReadId(batchProjectLayerTaskRead.getReadId());
                batchProjectLayerTaskReadBackup.setAddTime(LocalDateTime.now());
                batchProjectLayerTaskReadBackup.setUpdateTime(LocalDateTime.now());
                return batchProjectLayerTaskReadBackup;
            }).collect(Collectors.toList());
            for (BatchProjectLayerTaskReadBackup backUp : batchProjectLayerTaskReadBackups) {
                batchProjectLayerTaskReadBackupMapper.insert(backUp);
                //批处理分层任务来源字段备份
                List<BatchProjectLayerTaskReadField> batchProjectLayerTaskReadFields = batchProjectLayerTaskReadFieldMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskReadField>().eq(BatchProjectLayerTaskReadField::getReadId, backUp.getBackupReadId()));
                if (!ObjectUtils.isEmpty(batchProjectLayerTaskReadFields)) {
                    List<BatchProjectLayerTaskReadFieldBackup> batchProjectLayerTaskReadFieldBackups = batchProjectLayerTaskReadFields.stream().map(v -> {
                        BatchProjectLayerTaskReadFieldBackup batchProjectLayerTaskReadFieldBackup = new BatchProjectLayerTaskReadFieldBackup();
                        BeanUtils.copyProperties(v, batchProjectLayerTaskReadFieldBackup);
                        batchProjectLayerTaskReadFieldBackup.setReadFieldId(SecureUtil.md5(backupTaskId + v.getReadFieldId()));
                        batchProjectLayerTaskReadFieldBackup.setBackupReadFieldId(v.getReadFieldId());
                        batchProjectLayerTaskReadFieldBackup.setReadId(backUp.getReadId());
                        batchProjectLayerTaskReadFieldBackup.setBackupReadId(backUp.getBackupReadId());
                        batchProjectLayerTaskReadFieldBackup.setTaskId(backupTaskId);
                        batchProjectLayerTaskReadFieldBackup.setBackupTaskId(taskId);
                        batchProjectLayerTaskReadFieldBackup.setAddTime(LocalDateTime.now());
                        batchProjectLayerTaskReadFieldBackup.setUpdateTime(LocalDateTime.now());
                        return batchProjectLayerTaskReadFieldBackup;
                    }).collect(Collectors.toList());
                    for (BatchProjectLayerTaskReadFieldBackup readBackup : batchProjectLayerTaskReadFieldBackups) {
                        batchProjectLayerTaskReadFieldBackupMapper.insert(readBackup);
                    }
                }
            }
        }
        //批处理分层任务写入备份
        List<BatchProjectLayerTaskWrite> batchProjectLayerTaskWrites = batchProjectLayerTaskWriteMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskWrite>().eq(BatchProjectLayerTaskWrite::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskWrites)) {
            List<BatchProjectLayerTaskWriteBackup> batchProjectLayerTaskWriteBackups = batchProjectLayerTaskWrites.stream().map(batchProjectLayerTaskWrite -> {
                BatchProjectLayerTaskWriteBackup batchProjectLayerTaskWriteBackup = new BatchProjectLayerTaskWriteBackup();
                BeanUtils.copyProperties(batchProjectLayerTaskWrite, batchProjectLayerTaskWriteBackup);
                batchProjectLayerTaskWriteBackup.setWriteId(SecureUtil.md5(backupTaskId + batchProjectLayerTaskWrite.getWriteId()));
                batchProjectLayerTaskWriteBackup.setBackupWriteId(batchProjectLayerTaskWrite.getWriteId());
                batchProjectLayerTaskWriteBackup.setTaskId(backupTaskId);
                batchProjectLayerTaskWriteBackup.setBackupTaskId(taskId);
                batchProjectLayerTaskWriteBackup.setAddTime(LocalDateTime.now());
                batchProjectLayerTaskWriteBackup.setUpdateTime(LocalDateTime.now());
                return batchProjectLayerTaskWriteBackup;
            }).collect(Collectors.toList());
            for (BatchProjectLayerTaskWriteBackup backUp : batchProjectLayerTaskWriteBackups) {
                batchProjectLayerTaskWriteBackupMapper.insert(backUp);
                //批处理分层任务写入字段备份
                List<BatchProjectLayerTaskWriteField> batchProjectLayerTaskWriteFields = batchProjectLayerTaskWriteFieldMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskWriteField>().eq(BatchProjectLayerTaskWriteField::getWriteId, backUp.getBackupWriteId()));
                if (!ObjectUtils.isEmpty(batchProjectLayerTaskWriteFields)) {
                    List<BatchProjectLayerTaskWriteFieldBackup> batchProjectLayerTaskWriteFieldBackups = batchProjectLayerTaskWriteFields.stream().map(v -> {
                        BatchProjectLayerTaskWriteFieldBackup batchProjectLayerTaskWriteFieldBackup = new BatchProjectLayerTaskWriteFieldBackup();
                        BeanUtils.copyProperties(v, batchProjectLayerTaskWriteFieldBackup);
                        batchProjectLayerTaskWriteFieldBackup.setWriteFieldId(SecureUtil.md5(backupTaskId + v.getWriteFieldId()));
                        batchProjectLayerTaskWriteFieldBackup.setBackupWriteFieldId(v.getWriteFieldId());
                        batchProjectLayerTaskWriteFieldBackup.setWriteId(backUp.getWriteId());
                        batchProjectLayerTaskWriteFieldBackup.setBackupWriteId(backUp.getBackupWriteId());
                        batchProjectLayerTaskWriteFieldBackup.setTaskId(backupTaskId);
                        batchProjectLayerTaskWriteFieldBackup.setBackupTaskId(taskId);
                        batchProjectLayerTaskWriteFieldBackup.setAddTime(LocalDateTime.now());
                        batchProjectLayerTaskWriteFieldBackup.setUpdateTime(LocalDateTime.now());
                        return batchProjectLayerTaskWriteFieldBackup;
                    }).collect(Collectors.toList());

                    for (BatchProjectLayerTaskWriteFieldBackup writeBackup : batchProjectLayerTaskWriteFieldBackups) {
                        batchProjectLayerTaskWriteFieldBackupMapper.insert(writeBackup);
                    }
                }
            }
        }
        //批处理分层字段映射备份
        List<BatchProjectLayerTaskFieldMap> batchProjectLayerTaskFieldMaps = batchProjectLayerTaskFieldMapMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskFieldMap>().eq(BatchProjectLayerTaskFieldMap::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskFieldMaps)) {
            List<BatchProjectLayerTaskFieldMapBackup> batchProjectLayerTaskFieldMapBackups = batchProjectLayerTaskFieldMaps.stream().map(m -> {
                BatchProjectLayerTaskFieldMapBackup batchProjectLayerTaskFieldMapBackup = new BatchProjectLayerTaskFieldMapBackup();
                BeanUtils.copyProperties(m, batchProjectLayerTaskFieldMapBackup);
                batchProjectLayerTaskFieldMapBackup.setId(null);
                batchProjectLayerTaskFieldMapBackup.setBackupId(m.getId());
                batchProjectLayerTaskFieldMapBackup.setTaskId(backupTaskId);
                batchProjectLayerTaskFieldMapBackup.setBackupTaskId(taskId);

                batchProjectLayerTaskFieldMapBackup.setReadFieldId(SecureUtil.md5(backupTaskId + m.getReadFieldId()));
                batchProjectLayerTaskFieldMapBackup.setBackupReadFieldId(m.getReadFieldId());

                batchProjectLayerTaskFieldMapBackup.setReadId(SecureUtil.md5(backupTaskId + m.getReadId()));
                batchProjectLayerTaskFieldMapBackup.setBackupReadId(m.getReadId());

                batchProjectLayerTaskFieldMapBackup.setWriteFieldId(SecureUtil.md5(backupTaskId + m.getWriteFieldId()));
                batchProjectLayerTaskFieldMapBackup.setBackupWriteFieldId(m.getWriteFieldId());

                batchProjectLayerTaskFieldMapBackup.setWriteId(SecureUtil.md5(backupTaskId + m.getWriteId()));
                batchProjectLayerTaskFieldMapBackup.setBackupWriteId(m.getWriteId());

                batchProjectLayerTaskFieldMapBackup.setAddTime(LocalDateTime.now());
                batchProjectLayerTaskFieldMapBackup.setUpdateTime(LocalDateTime.now());
                return batchProjectLayerTaskFieldMapBackup;
            }).collect(Collectors.toList());
            for (BatchProjectLayerTaskFieldMapBackup backup : batchProjectLayerTaskFieldMapBackups) {
                batchProjectLayerTaskFieldMapBackupMapper.insert(backup);
            }
        }
        //通道控制备份
        List<BatchProjectLayerTaskChannel> batchProjectLayerTaskChannels = batchProjectLayerTaskChannelMapper.selectList(new LambdaUpdateWrapper<BatchProjectLayerTaskChannel>().eq(BatchProjectLayerTaskChannel::getTaskId, taskId));
        if (!ObjectUtils.isEmpty(batchProjectLayerTaskChannels)) {
            List<BatchProjectLayerTaskChannelBackup> batchProjectLayerTaskChannelBackups = batchProjectLayerTaskChannels.stream().map(v -> {
                BatchProjectLayerTaskChannelBackup batchProjectLayerTaskChannelBackup = new BatchProjectLayerTaskChannelBackup();
                BeanUtils.copyProperties(v, batchProjectLayerTaskChannelBackup);
                batchProjectLayerTaskChannelBackup.setChannelId(null);
                batchProjectLayerTaskChannelBackup.setBackupChannelId(v.getChannelId());
                batchProjectLayerTaskChannelBackup.setTaskId(backupTaskId);
                batchProjectLayerTaskChannelBackup.setBackupTaskId(taskId);
                batchProjectLayerTaskChannelBackup.setAddTime(LocalDateTime.now());
                batchProjectLayerTaskChannelBackup.setUpdateTime(LocalDateTime.now());
                return batchProjectLayerTaskChannelBackup;
            }).collect(Collectors.toList());
            for (BatchProjectLayerTaskChannelBackup backup : batchProjectLayerTaskChannelBackups) {
                batchProjectLayerTaskChannelBackupMapper.insert(backup);
            }
        }
    }

    @Override
    public void deleteLastTaskBackup(String taskId) {
        List<ProjectTaskBackupVo> backups = projectTaskBackupMapper.queryDeleteProjectTaskBackup(taskId);
        if (ObjectUtils.isEmpty(backups)) {
            return;
        }
        List<String> tasks = backups.stream().map(v -> v.getTaskId()).collect(Collectors.toList());
        projectTaskSchedueBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskSchedueBackup>().in(ProjectTaskSchedueBackup::getTaskId, tasks));
        projectTaskLazyBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskLazyBackup>().in(ProjectTaskLazyBackup::getTaskId, tasks));
        projectTaskEvnBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskEvnBackup>().in(ProjectTaskEvnBackup::getTaskId, tasks));
        projectTaskScriptBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskScriptBackup>().in(ProjectTaskScriptBackup::getTaskId, tasks));
        projectTaskBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskBackup>().in(ProjectTaskBackup::getTaskId, tasks));
        projectTaskFlowBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskFlowBackup>().in(ProjectTaskFlowBackup::getTaskId, tasks));
        projectTaskFlowNodeBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskFlowNodeBackup>().in(ProjectTaskFlowNodeBackup::getTaskId, tasks));
        projectTaskFlowSourceTargetNodeBackupMapper.delete(new LambdaUpdateWrapper<ProjectTaskFlowSourceTargetNodeBackup>().in(ProjectTaskFlowSourceTargetNodeBackup::getTaskId, tasks));
        batchProjectLayerTaskReadBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskReadBackup>().in(BatchProjectLayerTaskReadBackup::getTaskId, tasks));
        batchProjectLayerTaskWriteBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskWriteBackup>().in(BatchProjectLayerTaskWriteBackup::getTaskId, tasks));
        batchProjectLayerTaskFieldMapBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskFieldMapBackup>().in(BatchProjectLayerTaskFieldMapBackup::getTaskId, tasks));
        batchProjectLayerTaskChannelBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskChannelBackup>().in(BatchProjectLayerTaskChannelBackup::getTaskId, tasks));
        batchProjectLayerTaskReadFieldBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskReadFieldBackup>().in(BatchProjectLayerTaskReadFieldBackup::getTaskId, tasks));
        batchProjectLayerTaskWriteFieldBackupMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskWriteFieldBackup>().in(BatchProjectLayerTaskWriteFieldBackup::getTaskId, tasks));
    }

    @Override
    public List<ProjectTaskBackupVo> backups(ProjectTaskBackupDTO dto) {
        return projectTaskBackupMapper.queryProjectTaskBackupAll(dto);
    }

    @Override
    public List<ProjectTaskParameterBackup> queryParametersBackupByTaskId(ProjectTaskDTO param) {
        LambdaQueryWrapper<ProjectTaskParameterBackup> queryWrapper = new LambdaQueryWrapper<ProjectTaskParameterBackup>();
        queryWrapper.select(ProjectTaskParameterBackup::getParamKey, ProjectTaskParameterBackup::getParamValue);
        if (StringUtils.isNotBlank(param.getTaskId())) {
            queryWrapper.eq(ProjectTaskParameterBackup::getTaskId, param.getTaskId());
        }
        if (ObjectUtil.isNotEmpty(param.getTaskIds())) {
            queryWrapper.in(ProjectTaskParameterBackup::getTaskId, param.getTaskIds());
        }
        queryWrapper.orderByAsc(ProjectTaskParameterBackup::getId);
        return projectTaskParameterBackupMapper.selectList(queryWrapper).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public ProjectTaskVo queryProjectTaskBackupById(String taskId) {
        return projectTaskBackupMapper.queryProjectTaskBackupById(taskId);
    }

    @Override
    public ProjectTaskSchedueBackup queryProjectTaskSchedueBackupById(String taskId) {
        return projectTaskSchedueBackupMapper.selectById(taskId);
    }

    @Override
    public IPage<ProjectTaskLazyVo> queryProjectTaskLazyBackupPage(IPage<ProjectTaskLazyVo> page, ProjectTaskLazyDTO param) {
        return page.setRecords(projectTaskBackupMapper.queryProjectTaskLazyBackupPage(page, param));
    }

    @Override
    public BatchProjectLayerTaskWriteBackup queryBatchProjectLayerTaskWriteBakeByTaskId(String taskId) {
        return batchProjectLayerTaskWriteBackupMapper.selectOne(
                new LambdaUpdateWrapper<BatchProjectLayerTaskWriteBackup>()
                        .eq(BatchProjectLayerTaskWriteBackup::getTaskId, taskId)
        );
    }

    @Override
    public List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldBakeAll(BatchProjectLayerTaskReadFieldDTO param) {
        return projectTaskBackupMapper.queryBatchProjectLayerTaskReadFieldBakeupAll(param);
    }

    @Override
    public List<BatchProjectLayerTaskWriteFieldVo> queryBatchProjectLayerTaskWriteFieldBackup(String taskId) {
        List<BatchProjectLayerTaskWriteFieldBackup> list = batchProjectLayerTaskWriteFieldBackupMapper.selectList(new QueryWrapper<BatchProjectLayerTaskWriteFieldBackup>().lambda()
                .eq(BatchProjectLayerTaskWriteFieldBackup::getTaskId, taskId)
                .orderByAsc(BatchProjectLayerTaskWriteFieldBackup::getShowOrder));
        List<BatchProjectLayerTaskWriteFieldVo> finalList = list.stream().map(v -> {
            BatchProjectLayerTaskWriteFieldVo vo = new BatchProjectLayerTaskWriteFieldVo();
            BeanUtil.copyProperties(v, vo);
            return vo;
        }).collect(Collectors.toList());
        return finalList;
    }

    @Override
    public List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapBackupAll(BatchProjectLayerTaskFieldMapDTO param) {
        return projectTaskBackupMapper.queryBatchProjectLayerTaskFieldMapBackupAll(param);
    }

    @Override
    public BatchProjectLayerTaskChannelBackup queryBatchProjectLayerTaskChannelBackupByTaskId(String taskId) {
        return batchProjectLayerTaskChannelBackupMapper.selectOne(
                new LambdaUpdateWrapper<BatchProjectLayerTaskChannelBackup>()
                        .eq(BatchProjectLayerTaskChannelBackup::getTaskId, taskId)
        );
    }

    @Override
    public BatchProjectLayerTaskReadBackup getBatchTaskReadByTaskId(String taskId) {
        return batchProjectLayerTaskReadBackupMapper.selectOne(new QueryWrapper<BatchProjectLayerTaskReadBackup>().lambda().eq(BatchProjectLayerTaskReadBackup::getTaskId, taskId));
    }

    @Override
    public ProjectTaskScriptBackup queryProjectTaskScriptBackupById(String taskId) {
        return projectTaskScriptBackupMapper.selectById(taskId);
    }

    @Override
    public ProjectTaskFlowBackup queryProjectTaskFlowBackupById(String taskId) {
        return projectTaskFlowBackupMapper.selectById(taskId);
    }

    @Override
    public ProjectTaskEvnBackup queryTaskEvnById(String taskId) {
        return projectTaskEvnBackupMapper.selectOne(new QueryWrapper<ProjectTaskEvnBackup>().lambda().eq(ProjectTaskEvnBackup::getTaskId, taskId));
    }
}
