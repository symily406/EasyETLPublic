package com.easy.run.aspect;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.easy.etl.core.init.Config;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugWrite;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

@Aspect
public class ControllerAspect {
	private final String execution = "execution(* com.easy.system.*.controller.*.*(..))||execution(* com.easy.etl.*.controller.*.*(..))||execution(* com.easy.etl.*.*.controller.*.*(..))";

	@Value("${spring.resultLength:-1}")
	private int resultLength;



	@Before(execution)
	public void before(JoinPoint point) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();


		Signature signature = point.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		if (Config.dev) {
			DebugWrite.beforeWrite(request, methodSignature);
		}
	}

	@Around(execution)
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 原始的HTTP请求和响应的信息
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Signature signature = point.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		// 获取当前执行的方法
		// 获取参数
		Object[] objects = point.getArgs();
		// 获取参数
		Object[] arguments = new Object[objects.length];
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof ServletRequest || objects[i] instanceof ServletResponse
					|| objects[i] instanceof MultipartFile) {
				// 过滤掉不能序列化的参数
				continue;
			}
			arguments[i] = objects[i];
		}
		TimeInterval timer = DateUtil.timer();
		// 获取返回对象
		Object object = point.proceed();
		if (Config.dev) {
			DebugWrite.write(request, methodSignature, JsonKit.toJsonString(arguments), JsonKit.toJsonString(object),
					resultLength, timer.interval());
		}
		return object;
	}

}
