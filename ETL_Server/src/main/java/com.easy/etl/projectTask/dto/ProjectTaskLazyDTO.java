package com.easy.etl.projectTask.dto;


import com.easy.etl.projectTask.entity.ProjectTaskLazy;

import java.util.List;

/**
*  项目任务依懒-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskLazyDTO extends ProjectTaskLazy {
    private List<String> taskIds;

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }
}
