package com.easy.run;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.easy.etl.core.executor.ExecutorConfig;
import com.easy.etl.core.init.Initialization;
import com.easy.etl.core.init.Runner;
import com.easy.etl.core.scheduler.SchedulerConfig;
import com.easy.etl.redis.stream.consumer.MachineConsumer;
import com.easy.etl.redis.stream.consumer.ResultConsumer;
import com.easy.etl.redis.stream.handler.RedisStreamExecuteListener;
import com.easy.etl.redis.stream.handler.RedisStreamServerListener;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.easy.run.aspect.ControllerAspect;
import com.easy.run.cache.CacheManagement;
import com.easy.system.taskSchedule.init.TaskScheduleInitialization;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.spring.boot.enums.HttpEnum;
import com.spring.boot.exception.GlobalExceptionHandler;
import com.spring.boot.ext.plugin.cache.RedisConfigurer;
import com.spring.boot.ext.plugin.context.Application;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdGenerator;
import com.spring.boot.filter.auth.TokenFilter;
import com.spring.boot.filter.params.ParamsFilter;
import com.spring.boot.metainfo.MetaHandler;
import com.spring.boot.permissions.satoken.StpInterfaceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.easy.system.**.mapper",
        "com.easy.system.**.entity",
        "com.easy.etl.**.mapper",
        "com.easy.etl.**.entity"})
@ComponentScan({"com.easy.system.**.controller",
        "com.easy.system.**.service",
        "com.easy.etl.**.controller",
        "com.easy.etl.**.service"
})
@Import({Application.class, RedisConfigurer.class,
        StpInterfaceImpl.class, GlobalExceptionHandler.class,
        Initialization.class, TaskScheduleInitialization.class, MetaHandler.class,
        SchedulerConfig.class,
        ControllerAspect.class,
        Runner.class,
        ExecutorConfig.class,
        RedisStreamServerListener.class,
        ResultConsumer.class,
        MachineConsumer.class,
        RedisStreamService.class
})
public class SystemApplication implements WebMvcConfigurer {
    @Bean
    public IdentifierGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(MybatisConfiguration configuration) {
                configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOptimizeJoin(true);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有的当前站点的请求地址，都支持跨域访问。
                .allowedOriginPatterns("*") // 所有的外部域都可跨域访问。
                // 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
                .allowCredentials(true) // 是否支持跨域用户凭证
                .allowedMethods(new String[]{"GET", "POST"}) // 当前站点支持的跨域请求类型是什么
                .maxAge(3600); // 超时时长设置为1小时。 时间单位是秒。
    }

    /**
     * token过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenFilter());
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 注册过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ParamsFilter());
        registration.setOrder(2);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
    }

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            if (StpUtil.isLogin()) {//单点登陆
                String loginCode = String.valueOf(StpUtil.getExtra("loginCode"));
                int singlePointLogin = Integer.parseInt(StpUtil.getExtra("singlePointLogin").toString());
                if (!StringUtils.equals(CacheManagement.getLoginCode(), loginCode) && singlePointLogin == 1) {
                    throw new SaTokenException(HttpEnum.OFFLINE.getCode(), HttpEnum.OFFLINE.getMsg());
                }
            }
        })).addPathPatterns("/**");
    }


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication application = new SpringApplication(SystemApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
