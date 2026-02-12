package com.easy.etl.conf.utils;

import cn.hutool.core.io.FileUtil;
import com.easy.etl.common.conf.Yarn;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.conf.entity.ConfDatax;
import com.easy.etl.conf.entity.ConfSql;
import com.easy.etl.conf.entity.ConfYarn;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.machine.model.Sys;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;
import java.util.stream.Collectors;

public class ConfConvert {

    private static final String LINE_BREAK = "\n";
    private static final String ANNOTATE = "#";
    private static final String EQUAL_SIGN = "=";
    private static final List<String> NUMBER_COLUMN=Arrays.asList("jvm","xms","xmx","numContainers","containerMemory","masterMemory","priority");

    public static String convertToStr(ConfVo confVo, String workSpaceType) {
        String content = "";
        switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(workSpaceType))) {
            case SYNC:
                content = ConfConvert.dataxToStr(confVo.getConfDatax(), confVo.getConfYarn());
                break;
            case SQL:
            case SPARK_SQL:
                content = ConfConvert.sqlToStr(confVo.getConfSql(), confVo.getConfYarn());
                break;
        }
        return content;
    }

    /**
     * Datax配置转换成字符串
     *
     * @param confDatax
     * @param confYarn
     * @return
     */
    public static String dataxToStr(ConfDatax confDatax, ConfYarn confYarn) {
        StringBuilder datax = new StringBuilder();
        datax.append(yarnToStr(confYarn));
        datax.append(ANNOTATE).append("========同步任务配置========").append(LINE_BREAK);
        datax.append(ANNOTATE).append("jvm(单位:MB)").append(LINE_BREAK);
        datax.append(ANNOTATE).append("jvm").append(EQUAL_SIGN).append(confDatax.getDataxJvm()).append(LINE_BREAK);
        datax.append(ANNOTATE).append("AppName").append(LINE_BREAK);
        datax.append(ANNOTATE).append("appName").append(EQUAL_SIGN).append(confDatax.getDataxAppName()).append(LINE_BREAK);
        datax.append(ANNOTATE).append("队列").append(LINE_BREAK);
        datax.append(ANNOTATE).append("queue").append(EQUAL_SIGN).append(confDatax.getDataxQueue()).append(LINE_BREAK);
        return datax.toString();
    }

    public static String sqlToStr(ConfSql confSql, ConfYarn confYarn) {
        StringBuilder sql = new StringBuilder();
        sql.append(yarnToStr(confYarn));
        sql.append(ANNOTATE).append("==========sql任务配置==========").append(LINE_BREAK);
        sql.append(ANNOTATE).append("-Xms(单位:MB)初始堆内存大小").append(LINE_BREAK);
        sql.append(ANNOTATE).append("xms").append(EQUAL_SIGN).append(confSql.getExecuteXms()).append(LINE_BREAK);
        sql.append(ANNOTATE).append("-Xmx(单位:MB)最大堆内存大小").append(LINE_BREAK);
        sql.append(ANNOTATE).append("xmx").append(EQUAL_SIGN).append(confSql.getExecuteXmx()).append(LINE_BREAK);
        sql.append(ANNOTATE).append("AppName").append(LINE_BREAK);
        sql.append(ANNOTATE).append("appName").append(EQUAL_SIGN).append(confSql.getExecuteAppName()).append(LINE_BREAK);
        sql.append(ANNOTATE).append("队列").append(LINE_BREAK);
        sql.append(ANNOTATE).append("queue").append(EQUAL_SIGN).append(confSql.getExecuteQueue());
        return sql.toString();
    }

    public static String yarnToStr(ConfYarn confYarn) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANNOTATE).append("========集群配置========").append(LINE_BREAK);
        sb.append(ANNOTATE).append("========资源计算========").append(LINE_BREAK);
        sb.append(ANNOTATE).append("内存=numContainers*containerMemory+masterMemory").append(LINE_BREAK);
        sb.append(ANNOTATE).append("CPU=numContainers*containerVcores+masterVcores").append(LINE_BREAK);
        sb.append(ANNOTATE).append("任务容器（Task Container）的数量，建议1个").append(LINE_BREAK);
        sb.append(ANNOTATE).append("numContainers").append(EQUAL_SIGN).append(confYarn.getNumContainers()).append(LINE_BREAK);
        sb.append(ANNOTATE).append("每个任务容器的内存（MB），建议1024MB ~ 4096MB（1GB ~ 4GB）之间").append(LINE_BREAK);
        sb.append(ANNOTATE).append("containerMemory").append(EQUAL_SIGN).append(confYarn.getContainerMemory()).append(LINE_BREAK);
        sb.append(ANNOTATE).append("每个任务容器的CPU核心数").append(LINE_BREAK);
        sb.append(ANNOTATE).append("containerVcores").append(EQUAL_SIGN).append(confYarn.getContainerVcores()).append(LINE_BREAK);
        sb.append(ANNOTATE).append("AM容器的内存（MB）,建议1024MB ~ 4096MB（1GB ~ 4GB）之间").append(LINE_BREAK);
        sb.append(ANNOTATE).append("masterMemory").append(EQUAL_SIGN).append(confYarn.getMasterMemory()).append(LINE_BREAK);
        sb.append(ANNOTATE).append("AM容器的CPU核心数").append(LINE_BREAK);
        sb.append(ANNOTATE).append("masterVcores").append(EQUAL_SIGN).append(confYarn.getMasterVcores()).append(LINE_BREAK);
        sb.append(ANNOTATE).append("优先级").append(LINE_BREAK);
        sb.append(ANNOTATE).append("priority").append(EQUAL_SIGN).append(confYarn.getPriority()).append(LINE_BREAK);
        return sb.toString();
    }


    public static String parseConf(String evnContent) {
        Map<String,String> map=new HashMap<>();
        List<String> list = Arrays.asList(StringUtils.split(evnContent, LINE_BREAK));
        for (String s : list) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            if (StringUtils.indexOf(s, EQUAL_SIGN) == -1) {
                continue;
            }
            if (StringUtils.startsWith(s, ANNOTATE)) {
                continue;
            }
            String[] content = StringUtils.split(s, EQUAL_SIGN);
            if (content.length !=2){
                continue;
            }
            map.put(StringUtils.trim(content[0]),StringUtils.remove(StringUtils.trim(content[1]),LINE_BREAK));
        }
        if(map.size()==0){
            return null;
        }
        for (String column:NUMBER_COLUMN) {
            String val=map.get(column);
            if (!StringUtils.isNumeric(val)){
               map.remove(column);
            }
        }
        return JsonKit.toJsonString(map);
    }

    public static ConfVo getConfVo(ConfVo confVo,String evnContent, String workSpaceType) {
        if (StringUtils.isBlank(evnContent)){
            return confVo;
        }
        String conf = parseConf(evnContent);
        if (StringUtils.isBlank( conf)){
            return confVo;
        }
        ConfYarn targetYarn=confVo.getConfYarn();
        ConfYarn sourceYarn = JsonKit.parseObject(conf, ConfYarn.class);
        BeanUtils.copyProperties(sourceYarn,targetYarn,getNullPropertyNames(sourceYarn));
        confVo.setConfYarn(targetYarn);
        switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(workSpaceType))) {
            case SYNC:
                ConfDatax targetDatax=confVo.getConfDatax();
                ConfDatax sourceDatax = JsonKit.parseObject(conf, ConfDatax.class);
                BeanUtils.copyProperties(sourceDatax,targetDatax,getNullPropertyNames(sourceDatax));
                confVo.setConfDatax(targetDatax);
                break;
            case SQL:
            case SPARK_SQL:
                ConfSql targetSql=confVo.getConfSql();
                ConfSql sourceSql = JsonKit.parseObject(conf, ConfSql.class);
                BeanUtils.copyProperties(sourceSql,targetSql,getNullPropertyNames(sourceSql));
                confVo.setConfSql(targetSql);
                break;
        }
        return confVo;
    }

    /**
     * 获取为null的属性名数组
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


//    public static void main(String[] args) {
//        String content = FileUtil.readString("C:\\Users\\shaoyong\\Desktop\\新建 文本文档 (2).txt", "utf-8");
//        ConfVo confVo = new ConfVo();
//        ConfYarn yarn = new ConfYarn();
//        yarn.setPriority(1);
//        yarn.setYarnPath("d:/1.txt");
//        confVo.setConfYarn(yarn);
//        ConfSql confSql=new ConfSql();
//        confSql.setExecuteXms(1024);
//        confVo.setConfSql(confSql);
//        yarn.setNumContainers(10);
//        System.out.println(JsonKit.toJsonString(getConfVo(confVo, content, "2")));
//    }
}
