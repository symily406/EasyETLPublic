package com.easy.system.company.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.company.entity.CompanyInfo;

/**
 * 企业简介-接口
 * @author
 * @time
 */
@Mapper
public interface CompanyInfoMapper extends MapperDao<CompanyInfo> {


}
