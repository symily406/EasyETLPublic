package com.easy.system.staff.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.company.entity.Company;
import com.easy.system.company.mapper.CompanyMapper;
import com.easy.system.staff.dto.StaffDTO;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.entity.StaffDuty;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.mapper.StaffDutyMapper;
import com.easy.system.staff.mapper.StaffInfoMapper;
import com.easy.system.staff.mapper.StaffMapper;
import com.easy.system.staff.mapper.StaffRoleMapper;
import com.easy.system.staff.service.IStaffService;
import com.easy.system.staff.vo.StaffVo;

/**
 * 用户表-服务类
 *
 * @author
 * @time
 */
@Service
public class StaffServiceImpl extends BaseServiceImpl<StaffMapper, Staff> implements IStaffService {

    @Autowired
    private StaffRoleMapper staffRoleMapper;

    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private StaffDutyMapper staffDutyMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private StaffMapper staffMapper;


    /**
     * 修改登陆编码、登陆次数
     */
    @Override
    public void updateLogin(String staffId, String loginCode, int loginNum) {
        Staff staff = new Staff();
        staff.setStaffId(staffId);
        staff.setLoginCode(loginCode);
        staff.setLoginNum(loginNum);
        baseMapper.updateById(staff);
    }

    /**
     * 修改密码
     */
    public boolean updatePassword(Staff staff) {
        staffMapper.updateById(staff);
        return true;
    }

    /**
     * 用户登陆
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public StaffVo userLogin(String userName) {
        return baseMapper.userLogin(userName);
    }


    /**
     * 用户刷新
     *
     * @param staffId 用户ID
     * @return
     */
    @Override
    public StaffVo userInfoRefresh(String staffId) {
        return baseMapper.userInfoRefresh(staffId);
    }


    /**
     * 保存或修改Entity
     */
    @Transactional
    @Override
    public boolean saveOrUpdateStaff(Staff staff, StaffInfo staffInfo, List<String> roles, List<String> dutyIds) {
        if (StringUtils.isBlank(staff.getStaffId())) {
            staff.setStaffId(idBulider());
        }
        Company company = companyMapper.selectById(staff.getCompanyId());
        staffRoleMapper.deleteByCondition("DELETE FROM TR_STAFF_ROLE WHERE STAFF_ID='" + SqlKit.filter(staff.getStaffId()) + "'", null);
        staffDutyMapper.deleteByCondition("DELETE FROM TD_STAFF_DUTY WHERE STAFF_ID='" + SqlKit.filter(staff.getStaffId()) + "'", null);
        if (roles != null && !roles.isEmpty()) {
            for (String role : roles) {
                StaffRole staffRole = new StaffRole();
                staffRole.setStaffId(staff.getStaffId());
                staffRole.setRoleId(role);
                staffRole.insert();
            }
        }

        if (dutyIds != null && !dutyIds.isEmpty()) {
            for (String dutyId : dutyIds) {
                StaffDuty duty = new StaffDuty();
                duty.setStaffId(staff.getStaffId());
                duty.setDutyId(dutyId);
                duty.insert();
            }
        }

        staffInfo.setStaffId(staff.getStaffId());
        staffInfo.insertOrUpdate();

        staff.setLeval(company.getLeval());


        return saveOrUpdate(staff);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<StaffVo> queryStaffPage(IPage<StaffVo> page, StaffDTO param) {
        return page.setRecords(baseMapper.queryStaffPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<StaffVo> queryStaffAll(StaffDTO param) {
        return baseMapper.queryStaffAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public StaffVo queryStaffByCondition(StaffDTO param) {
        return baseMapper.queryStaffByCondition(param);
    }

    /**
     * 根据主键查询单个用户
     *
     * @param param
     * @return
     */
    @Override
    public StaffVo queryStaffById(StaffDTO param) {
        return baseMapper.queryStaffById(param);
    }


}
