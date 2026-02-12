package com.easy.system.login.controller;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.enums.HttpEnum;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.JwtKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import com.easy.run.cache.CacheManagement;
import com.easy.etl.core.init.Config;
import com.easy.system.company.entity.Company;
import com.easy.system.company.service.ICompanyService;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.service.IDutyService;
import com.easy.system.indexPage.entity.IndexPage;
import com.easy.system.indexPage.service.IIndexPageService;
import com.easy.system.login.dto.LoginDTO;
import com.easy.system.login.vo.LoginVo;
import com.easy.system.role.entity.Role;
import com.easy.system.role.service.IRoleService;
import com.easy.system.staff.service.IStaffService;
import com.easy.system.staff.vo.StaffVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;

@Validated
@RestController
@RequestMapping(value = "/admin/system")
public class LoginController extends BaseController {

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IDutyService dutyService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IIndexPageService indexPageService;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 登陆
     */
    @SaIgnore
    @DebugAnnotation(desc = "管理员登陆", logType = 1)
    @PostMapping(value = "/login")
    public ApiResult<LoginVo> login(@Validated LoginDTO param) {

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(param.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        if (!response.isSuccess()) {
            return ApiResult.fail(response.getRepMsg());
        }
        StaffVo staff = staffService.userLogin(param.getUserName());
        if (staff == null) {
            return ApiResult.fail(HttpEnum.PASSWORD_OR_ACCOUNT_ERROR);
        }

        if (!StringUtils.equals(staff.getPassword(), SecureUtil.md5(param.getPassWord().concat(staff.getSalt())))) {
            return ApiResult.fail(HttpEnum.PASSWORD_OR_ACCOUNT_ERROR);
        }
        if (staff.getEnable() == 0) {
            return ApiResult.fail(HttpEnum.ACCOUNT_DISABLE);
        }
        if (staff.getIsLock() == 1) {
            return ApiResult.fail(HttpEnum.ACCOUNT_LOCK);
        }
        long day = DateUtil.between(new Date(), DateUtil.parseDate(staff.getExpireTime()), DateUnit.DAY, false);
        if (day <= 0) {
            return ApiResult.fail(HttpEnum.ACCOUNT_EXCEPTION);
        }

        String loginCode = IdBulider.id();
        staff.setLoginCode(loginCode);
        // 修改登陆编码及登陆次数
        staffService.updateLogin(staff.getStaffId(), loginCode, staff.getLoginNum() + 1);

        Duty duty = dutyService.queryDutyByStaffId(staff.getStaffId());
        staff.setDutyLeval(duty.getDutyLeval());

        LoginVo loginVo = createToken(staff);
        CacheManagement.clearStaffCache();
        return ApiResult.ok(loginVo);
    }

    /**
     * 刷新用户信息
     */
    @DebugAnnotation(desc = "刷新用户信息", logType = 1)
    @PostMapping(value = "/refreshToken")
    public ApiResult<LoginVo> refreshToken(@NotEmpty(message = "token不能为空") String token) {
        String staffId = JwtKit.getValue(Config.refreshClaimName, token);
        if (StringUtils.isBlank(staffId)) {
            return ApiResult.fail(HttpEnum.REFRESH_TOKEN_EXCEPTION.getCode(),
                    HttpEnum.REFRESH_TOKEN_EXCEPTION.getMsg());
        }
        StaffVo staff = staffService.userInfoRefresh(staffId);
        // 校验用户是否存在
        if (staff == null) {
            return ApiResult.fail(HttpEnum.REFRESH_TOKEN_EXCEPTION.getCode(),
                    HttpEnum.REFRESH_TOKEN_EXCEPTION.getMsg());
        }
        String salt = SecureUtil.md5(staff.getSalt());

        if (!JwtKit.verify(Config.refreshClaimName, token, salt)) {
            return ApiResult.fail(HttpEnum.REFRESH_TOKEN_EXCEPTION.getCode(),
                    HttpEnum.REFRESH_TOKEN_EXCEPTION.getMsg());
        }
        Duty duty = dutyService.queryDutyByStaffId(staff.getStaffId());
        staff.setDutyLeval(duty.getDutyLeval());
        return ApiResult.ok(createToken(staff));
    }

    /**
     * 生成用户登陆令牌及刷新令牌
     *
     * @param staff
     * @return
     */
    private LoginVo createToken(StaffVo staff) {
        SaLoginModel model = SaLoginConfig.setExtra("staffId", staff.getStaffId())
                .setExtra("companyId", staff.getCompanyId()).setExtra("companyName", staff.getCompanyName())
                .setExtra("orgId", staff.getOrgId()).setExtra("orgName", staff.getOrgName())
                .setExtra("leval", staff.getLeval()).setExtra("dutyLeval", staff.getDutyLeval())
                .setExtra("loginCode", staff.getLoginCode())
                .setExtra("userRelName", staff.getName())
                .setExtra("singlePointLogin", staff.getIsSinglePointLogin());

        // 登陆
        StpUtil.login(staff.getStaffId(), model);
        // 设置用户编码缓存
        CacheManagement.setLoginCode();

        String token = StpUtil.getTokenValue();

        // 刷新token
        String salt = SecureUtil.md5(staff.getSalt());
        String refreshToken = JwtKit.sign(Config.refreshClaimName, staff.getStaffId(), salt, Config.refreshExpireTime);

        LoginVo vo = new LoginVo();
        vo.setToken(token);
        vo.setRefreshToken(refreshToken);
        vo.setName(staff.getName());
        vo.setLeval(staff.getLeval());

        String sql = "SELECT D.NAME,D.INDEX_PATH,D1.SHOW_ORDER FROM TD_ROLE D LEFT JOIN TD_DICT_VALUES D1 ON D.CODE=D1.VALUE AND D1.DICT_CODE='ROLE_CODE'  "
                + "WHERE D.R_ID IN (SELECT ROLE_ID FROM TR_STAFF_ROLE WHERE STAFF_ID='" + staff.getStaffId()
                + "') AND D.IS_DEL=1 ORDER BY D1.SHOW_ORDER ASC";
        List<Role> roles = roleService.findBySql(sql, Role.class);
        if (roles != null && !roles.isEmpty()) {
            Role role = roles.get(0);
            if (StringUtils.isNotBlank(role.getIndexPath())) {
                IndexPage indexPage = indexPageService.getById(role.getIndexPath());
                if (indexPage != null) {
                    vo.setIsExternal(indexPage.getIsExternal());
                    vo.setRoutePath(indexPage.getRoutePath());
                    vo.setUrl(indexPage.getUrl());
                }
            }
        }
        if (StringUtils.isBlank(vo.getRoutePath())) {
            Company company = companyService.getById(staff.getCompanyId());
            if (company != null) {
                if (StringUtils.isNotBlank(company.getIndexPath())) {
                    IndexPage indexPage = indexPageService.getById(company.getIndexPath());
                    if (indexPage != null) {
                        vo.setIsExternal(indexPage.getIsExternal());
                        vo.setRoutePath(indexPage.getRoutePath());
                        vo.setUrl(indexPage.getUrl());
                    }
                }
                vo.setPlatformName(company.getPlatformName());
            }
        }
        return vo;
    }

    /**
     * 退出登陆
     */
    @SaIgnore
    @DebugAnnotation(desc = "退出登陆", logType = 1)
    @GetMapping(value = "/loginOut")
    public ApiResult loginOut() {
        CacheManagement.clearStaffCache();
        StpUtil.logout();
        return ApiResult.ok();
    }
}
