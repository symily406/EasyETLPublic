package com.easy.etl.conf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.init.Config;
import com.easy.etl.conf.dto.ConfDTO;
import com.easy.etl.conf.entity.*;
import com.easy.etl.conf.mapper.*;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.run.cache.CacheManagement;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 系统配置主表-服务类
 * @author 邵勇
 * @time 2025-10-08
 */
@Service
public class ConfServiceImpl extends BaseServiceImpl<ConfMapper, Conf> implements IConfService {

	@Autowired
	private ConfBaseMapper confBaseMapper;
	@Autowired
	private ConfYarnMapper confYarnMapper;
	@Autowired
	private ConfDataxMapper confDataxMapper;
	@Autowired
	private ConfSqlMapper confSqlMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean setDefault(String confId) {
		baseMapper.update(null, new LambdaUpdateWrapper<Conf>().set(Conf::getIsDefault, 0));
		baseMapper.update(null, new LambdaUpdateWrapper<Conf>().set(Conf::getIsDefault, 1).eq(Conf::getConfId, confId));
		CacheManagement.clearConf();
		return true;
	}

	@Override
	public ConfVo getConf() {
		ConfVo confVo= CacheManagement.getConf();
		if (!ObjectUtils.isEmpty(confVo)){
			return confVo;
		}
		List<Conf> confs=baseMapper.selectList(new LambdaQueryWrapper<Conf>().orderByDesc(Conf::getAddTime));
		if (ObjectUtils.isEmpty(confs)){
			return null;
		}
		Conf defaultConf=confs.stream().filter(v->v.getIsDefault()==1).findFirst().orElse(null);
		if (ObjectUtils.isEmpty(defaultConf)){
			defaultConf=confs.get(0);
		}
		confVo=new ConfVo();
		BeanUtils.copyProperties(defaultConf, confVo);
		confVo.setConfBase(confBaseMapper.selectById(confVo.getConfId()));
		confVo.setConfYarn(confYarnMapper.selectById(confVo.getConfId()));
		confVo.setConfDatax(confDataxMapper.selectById(confVo.getConfId()));
		confVo.setConfSql(confSqlMapper.selectById(confVo.getConfId()));
		CacheManagement.setConf(confVo);
		return confVo;
	}

	@Override
	public ConfVo queryConfById(String confId) {
		ConfVo confVo= new ConfVo();
		Conf conf=baseMapper.selectById(confId);
		if (ObjectUtils.isEmpty(conf)){
			return confVo;
		}
		BeanUtils.copyProperties(conf, confVo);
		confVo.setConfBase(confBaseMapper.selectById(confVo.getConfId()));
		confVo.setConfYarn(confYarnMapper.selectById(confVo.getConfId()));
		confVo.setConfDatax(confDataxMapper.selectById(confVo.getConfId()));
		confVo.setConfSql(confSqlMapper.selectById(confVo.getConfId()));
		return confVo;
	}

	/**
	 * 保存或修改Entity
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateConf(Conf conf, ConfBase confBase, ConfYarn confYarn, ConfDatax confDatax, ConfSql confSql) {
		String confId=conf.getConfId();
		if (StringUtils.isBlank(confId)){
			confId= IdBulider.id();
		}
		confBase.setId(confId);
		confBase.setConfId(confId);
		confBaseMapper.deleteById(confId);
		confBaseMapper.insert(confBase);

		confYarn.setId(confId);
		confYarn.setConfId(confId);
		confYarnMapper.deleteById(confId);
		confYarnMapper.insert(confYarn);

		confDatax.setId(confId);
		confDatax.setConfId(confId);
		confDataxMapper.deleteById(confId);
		confDataxMapper.insert(confDatax);

		confSql.setId(confId);
		confSql.setConfId(confId);
		confSqlMapper.deleteById(confId);
		confSqlMapper.insert(confSql);

		conf.setConfId(confId);
		return saveOrUpdate(conf);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<ConfVo> queryConfPage(IPage<ConfVo> page, ConfDTO param) {
		return page.setRecords(baseMapper.queryConfPage(page, param));
	}
}
