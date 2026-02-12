package com.easy.etl.conf.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.conf.dto.ConfDTO;
import com.easy.etl.conf.entity.Conf;
import com.easy.etl.conf.vo.ConfVo;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 系统配置主表-接口
 *
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface ConfMapper extends MapperDao<Conf> {

    /**
     * 分页
     * @param param
     * @return
     */
    List<ConfVo> queryConfPage(IPage<ConfVo> page, @Param("param") ConfDTO param);
}
