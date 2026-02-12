package com.easy.system.operationLog.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.init.Config;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.operationLog.dto.OperationLogDTO;
import com.easy.system.operationLog.entity.OperationLog;
import com.easy.system.operationLog.entity.OperationLogInfo;
import com.easy.system.operationLog.mapper.OperationLogInfoMapper;
import com.easy.system.operationLog.mapper.OperationLogMapper;
import com.easy.system.operationLog.service.IOperationLogService;
import com.easy.system.operationLog.vo.OperationLogVo;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.util.ObjectUtils;

/**
 * 操作日志-服务类
 *
 * @author
 * @time
 */
@Service
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog>
        implements IOperationLogService {
    @Autowired
    private OperationLogInfoMapper operationLogInfoMapper;

    /**
     * 批量删除-真删
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchDelete(List<String> ids) {
        operationLogInfoMapper.delete(new LambdaQueryWrapper<OperationLogInfo>().in(OperationLogInfo::getLogId, ids));
        baseMapper.delete(new LambdaUpdateWrapper<OperationLog>().in(OperationLog::getLogId, ids));
        return true;
    }

    /**
     * 保存或修改Entity
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateOperationLog(HttpServletRequest request, MethodSignature methodSignature, String params,
                                            String result, int resultLength, Long runTimer) {
        // 获取地址
        String url = request.getRequestURL().toString();
        // 获取 session
        HttpSession session = request.getSession();
        String id = session.getId();
        String requestMethod = request.getMethod();
        String servletPath = request.getServletPath();

        // 获取当前执行的方法
        Method method = methodSignature.getMethod();
        DebugAnnotation logAnnotation = method.getAnnotation(DebugAnnotation.class);
        if (logAnnotation != null) {
            String desc = logAnnotation.desc();
            String declaringClass = method.getDeclaringClass().getName();
            String controller = declaringClass.concat(".").concat(method.getName()).concat("()");
            OperationLog log = new OperationLog();
            log.setLogId(id);
            log.setName(desc);
            log.setMode(requestMethod);
            log.setUrl(url);
            log.setController(controller);
            log.setMethod(method.getName());
            log.setIp(ServletUtil.getClientIP(request, null));
            log.setUsageTime(runTimer);
            if (logAnnotation.logType() == 0) {
                log.setOrgId(StpUtil.getExtra("orgId").toString());
                log.setCompanyId(StpUtil.getExtra("companyId").toString());
                log.setAddUserId(StpUtil.getExtra("staffId").toString());
            }
            log.setLogType(logAnnotation.logType());
            log.setEnable(1);
            saveOrUpdate(log);

            OperationLogInfo info = new OperationLogInfo();
            info.setLogId(id);
            if (resultLength != -1) {
                params = StringUtils.substring(params, 0, resultLength);
                result = StringUtils.substring(result, 0, resultLength);
            }
            info.setRequestInfo(params);
            info.setResponseInfo(result);
            operationLogInfoMapper.insert(info);
        }
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
    public IPage<OperationLogVo> queryOperationLogPage(IPage<OperationLogVo> page, OperationLogDTO param) {
        return page.setRecords(baseMapper.queryOperationLogPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<OperationLogVo> queryOperationLogAll(OperationLogDTO param) {
        return baseMapper.queryOperationLogAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public OperationLogVo queryOperationLogByCondition(OperationLogDTO param) {
        return baseMapper.queryOperationLogByCondition(param);
    }

}
