package com.easy.etl.task.service;

import com.easy.system.taskSchedule.entity.TaskSchedule;

public interface ITaskService {
   /**
    * 处理已运行的任务
    */
   void handleRunTaskJob();

   /**
    * 添加任务到运行列表
    */
   void addRunTaskJob();

   /**
    * 处理已可运行的任务
    */
   void handleTaskJob();

   void  run(TaskSchedule taskSchedule);
}
