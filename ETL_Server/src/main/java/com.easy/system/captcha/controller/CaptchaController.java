package com.easy.system.captcha.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.easy.etl.core.controller.BaseController;
import com.spring.boot.enums.HttpEnum;
import com.spring.boot.ext.api.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 *
 * @author
 */
@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/get")
    public ApiResult get(CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(getRemoteId(request));
        ResponseModel responseModel = captchaService.get(data);
        if (!responseModel.isSuccess()) {
            return ApiResult.fail(responseModel.getRepMsg());
        }
        return ApiResult.ok(responseModel.getRepData());
    }

    @PostMapping("/check")
    public ApiResult check(CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        ResponseModel responseModel = captchaService.check(data);
        return ApiResult.ok(responseModel.getRepData());
    }

    /***
     * 服务端验证接口，独立部署的场景使用，集成部署的场景：服务内部调用，不需要调用此接口可注释掉
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/verify")
    public ApiResult verify(CaptchaVO data, HttpServletRequest request) {
        ResponseModel responseModel = captchaService.verification(data);
        return ApiResult.ok(responseModel.getRepData());
    }

    public static final String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        if (StringUtils.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        }
        return null;
    }
}
