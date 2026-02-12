package com.easy.etl.projectTask.vo;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTaskTreeVo {
    private String id;
    private String pid;
    private String name;

    private boolean isMenu;
    private boolean isClose;
    private int leval;
    private int taskType;
    private int workSpaceType;

    private String projectId;

    private String projectWorkSpaceId;

    private String projectWorkSpaceLayerRelId;

    private int childrenSize;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProjectTaskTreeVo> children;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public int getLeval() {
        return leval;
    }

    public void setLeval(int leval) {
        this.leval = leval;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(String  taskType) {
        this.taskType = NumberUtil.parseInt(taskType);
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(int workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = NumberUtil.parseInt(workSpaceType);
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

    public int getChildrenSize() {

        if (getChildren()==null||getChildren().isEmpty()){
            return 0;
        }
        List<ProjectTaskTreeVo> childer=getChildren().stream().filter(v->v.getLeval()!=3).collect(Collectors.toList());
        return childer.size();
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    public List<ProjectTaskTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectTaskTreeVo> children) {
        this.children = children;
    }

    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getLabel() {
        return getName();
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
