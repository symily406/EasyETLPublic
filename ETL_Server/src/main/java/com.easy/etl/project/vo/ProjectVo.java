package com.easy.etl.project.vo;


import com.easy.etl.project.entity.Project;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目管理-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectVo extends Project {
    /**
     * 总任务数
     */
    private BigDecimal totalTask = BigDecimal.ZERO;


    /**
     * 失败任务数
     */
    private BigDecimal failTask = BigDecimal.ZERO;

    /**
     * 表数量
     */
    private BigDecimal tableNum = BigDecimal.ZERO;

    /**
     * 占用存储空间
     */
    private BigDecimal storageSpace = BigDecimal.ZERO;

    /**
     * 引入数据源量
     */
    private BigDecimal dbSourceNum;


    private List<ProjectMemberWorkSpaceVo> memberWorkSpaceVos;

    public BigDecimal getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(BigDecimal totalTask) {
        this.totalTask = totalTask;
    }

    public BigDecimal getFailTask() {
        return failTask;
    }

    public void setFailTask(BigDecimal failTask) {
        this.failTask = failTask;
    }

    public BigDecimal getTableNum() {
        return tableNum;
    }

    public void setTableNum(BigDecimal tableNum) {
        this.tableNum = tableNum;
    }

    public BigDecimal getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(BigDecimal storageSpace) {
        this.storageSpace = storageSpace;
    }

    public BigDecimal getDbSourceNum() {
        return dbSourceNum;
    }

    public void setDbSourceNum(BigDecimal dbSourceNum) {
        this.dbSourceNum = dbSourceNum;
    }

    public List<ProjectMemberWorkSpaceVo> getMemberWorkSpaceVos() {
        return memberWorkSpaceVos;
    }

    public void setMemberWorkSpaceVos(List<ProjectMemberWorkSpaceVo> memberWorkSpaceVos) {
        this.memberWorkSpaceVos = memberWorkSpaceVos;
    }
}
