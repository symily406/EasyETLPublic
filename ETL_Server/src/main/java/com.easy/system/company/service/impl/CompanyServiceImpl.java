package com.easy.system.company.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.dto.InitCompanyDTO;
import com.easy.system.company.entity.Company;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.entity.CompanyInfo;
import com.easy.system.company.mapper.CompanyFunctionMapper;
import com.easy.system.company.mapper.CompanyMapper;
import com.easy.system.company.service.ICompanyService;
import com.easy.system.company.vo.CompanyFunctionVo;
import com.easy.system.company.vo.CompanyVo;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.mapper.DutyMapper;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.mapper.FileConfigMapper;
import com.easy.system.org.entity.Org;
import com.easy.system.org.mapper.OrgMapper;
import com.easy.system.role.entity.Role;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.mapper.RoleFunctionMapper;
import com.easy.system.role.mapper.RoleMapper;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.entity.StaffDuty;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.mapper.StaffDutyMapper;
import com.easy.system.staff.mapper.StaffInfoMapper;
import com.easy.system.staff.mapper.StaffMapper;
import com.easy.system.staff.mapper.StaffRoleMapper;

/**
 * 企业-服务类
 *
 * @author
 * @time
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Autowired
    private CompanyFunctionMapper companyFunctionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DutyMapper dutyMapper;

    @Autowired
    private FileConfigMapper fileConfigMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleFunctionMapper roleFunctionMapper;

    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private StaffRoleMapper staffRoleMapper;

    @Autowired
    private StaffDutyMapper staffDutyMapper;

    /**
     * 初始化企业信息
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean initCompany(InitCompanyDTO param) {


        // 添加机构
        String orgId = idBulider();
        Org org = new Org();
        org.setOrgId(orgId);
        org.setPOrgId("0");
        org.setOrgPath("0");
        org.setCompanyId(param.getCompanyId());
        org.setOrgFullName(param.getInitOrgFullName());
        org.setOrgName(param.getInitOrgName());
        org.setOrgCode(param.getInitOrgCode());
        org.setShowOrder(100);
        org.setEnable(1);
        org.setAllowDelete(1);
        org.setAllowEdit(1);
        String orgPath = org.getOrgPath().concat("|").concat(org.getPOrgId()).concat("|").concat(org.getOrgId());
        org.setOrgPath(StrKit.clearDuplicateValue(orgPath, "|"));
        orgMapper.insert(org);

        // 添加角色
        String roleId = idBulider();
        Role role = new Role();
        role.setrId(roleId);
        role.setCode(param.getInitRoleCode());
        role.setName(param.getInitRoleName());
        role.setCompanyId(param.getCompanyId());
        role.setOrgId(orgId);
        role.setEnable(1);
        role.setAllowDelete(1);
        role.setAllowEdit(1);
        roleMapper.insert(role);
        // 添加角色权限
        CompanyFunctionDTO companyFunctionDTO = new CompanyFunctionDTO();
        companyFunctionDTO.setCompanyId(param.getCompanyId());
        List<CompanyFunctionVo> funs = companyFunctionMapper.queryCompanyFunctionAll(companyFunctionDTO);
        for (CompanyFunctionVo companyFunctionVo : funs) {
            RoleFunction roleFunction = new RoleFunction();
            roleFunction.setrId(roleId);
            roleFunction.setfId(companyFunctionVo.getfId());
            roleFunction.setLeval(companyFunctionVo.getLeval());
            roleFunctionMapper.insert(roleFunction);
        }
        // 添加岗位
        String initSysDutyId = idBulider();
        Duty duty = new Duty();
        duty.setDutyId(initSysDutyId);
        duty.setDutyName("系统管理员");
        duty.setDutyLeval(5);
        duty.setShowOrder(100);
        duty.setCompanyId(param.getCompanyId());
        duty.setOrgId(orgId);
        duty.setEnable(1);
        duty.setAllowDelete(1);
        duty.setAllowEdit(1);
        dutyMapper.insert(duty);

        duty = new Duty();
        duty.setDutyName("普通用户");
        duty.setDutyLeval(0);
        duty.setShowOrder(90);
        duty.setCompanyId(param.getCompanyId());
        duty.setOrgId(orgId);
        dutyMapper.insert(duty);

        //添加用户
        String staffId = idBulider();
        //用户信息
        StaffInfo staffInfo = new StaffInfo();
        staffInfo.setStaffId(staffId);
        staffInfo.setName(param.getInitName());
        staffInfo.setPhone(param.getInitPhone());
        staffInfo.setSex(param.getInitSex());
        staffInfoMapper.insert(staffInfo);
        //用户
        Staff staff = new Staff();
        staff.setStaffId(staffId);
        staff.setUserName(param.getInitUserName());
        staff.setPassword(param.getInitPassword());
        staff.setExpireTime(param.getInitExpireTime());
        staff.setSalt(param.getInitSalt());
        staff.setCompanyId(param.getCompanyId());
        staff.setOrgId(orgId);
        staff.setIsLock(0);
        staff.setEnable(1);
        staff.setAllowDelete(1);
        staff.setAllowEdit(1);
        staff.setLeval(0);
        staffMapper.insert(staff);
        //用户角色关联
        StaffRole staffRole = new StaffRole();
        staffRole.setRoleId(roleId);
        staffRole.setStaffId(staffId);
        staffRoleMapper.insert(staffRole);
        //用户岗位
        StaffDuty staffDuty = new StaffDuty();
        staffDuty.setDutyId(initSysDutyId);
        staffDuty.setStaffId(staffId);
        staffDutyMapper.insert(staffDuty);
        //文件上传配置
        List<FileConfig> fileConfigs = fileConfigMapper.selectList(new QueryWrapper<FileConfig>().lambda().eq(FileConfig::getLeval, 100));
        for (FileConfig fileConfig : fileConfigs) {
            FileConfig config = new FileConfig();
            config.setFileType(fileConfig.getFileType());
            config.setSaveType(fileConfig.getSaveType());
            config.setName(fileConfig.getName());
            config.setAccept(fileConfig.getAccept());
            config.setChunkSize(fileConfig.getChunkSize());
            config.setCompanyId(param.getCompanyId());
            config.setOrgId(orgId);
            fileConfigMapper.insert(config);
        }
        Company company = new Company();
        company.setCompanyId(param.getCompanyId());
        company.setIsInit(1);
        baseMapper.updateById(company);
        return true;
    }

    /**
     * 企业部门下拉选择
     *
     * @param param
     * @return
     */
    @Override
    public List<CompanyVo> queryCompanyDropDown(CompanyDTO param) {
        return baseMapper.queryCompanyDropDown(param);
    }

    /**
     * 保存或修改Entity
     *
     * @param company
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateCompany(Company company, CompanyInfo companyInfo, List<CompanyFunctionDTO> menus) {
        boolean succed = true;
        try {
            String companyId = company.getCompanyId();
            if (StringUtils.isBlank(companyId)) {
                companyId = idBulider();
                company.setCompanyId(companyId);
                companyMapper.insert(company);
            } else {
                company.setCompanyId(companyId);
                companyMapper.updateById(company);
            }
            companyInfo.setCompanyId(companyId);
            companyInfo.insertOrUpdate();

            companyFunctionMapper.deleteByCondition(
                    "DELETE FROM TD_COMPANY_FUNCTION D WHERE D.COMPANY_ID='" + SqlKit.filter(companyId) + "'", null);
            for (CompanyFunctionDTO menu : menus) {
                CompanyFunction fun = new CompanyFunction(idBulider(), companyId, menu.getfId(), menu.getLeval());
                companyFunctionMapper.insert(fun);
            }
            roleFunctionMapper.deleteByCondition(
                    "DELETE FROM TR_ROLE_FUNCTION  WHERE R_ID IN(SELECT D.R_ID FROM TD_ROLE D WHERE D.COMPANY_ID='"
                            + SqlKit.filter(companyId)
                            + "') AND F_ID NOT IN(SELECT D.F_ID FROM TD_COMPANY_FUNCTION D WHERE D.COMPANY_ID='"
                            + SqlKit.filter(companyId) + "')",
                    null);
        } catch (Exception e) {
            succed = false;
            e.printStackTrace();
        }
        return succed;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<CompanyVo> queryCompanyPage(IPage<CompanyVo> page, CompanyDTO param) {
        return page.setRecords(baseMapper.queryCompanyPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<CompanyVo> queryCompanyAll(CompanyDTO param) {
        return baseMapper.queryCompanyAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public CompanyVo queryCompanyByCondition(CompanyDTO param) {
        return baseMapper.queryCompanyByCondition(param);
    }

}
