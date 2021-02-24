package com.smm.frame.annotation;

import com.smm.frame.config.exception.GlobalExceptionHandler;
import com.smm.frame.config.mybatis.GlobalMybatisConfig;
import com.smm.frame.config.redis.GlobalRedisConfig;
import com.smm.frame.config.rest.RestTemplateAutoConfig;
import com.smm.frame.config.webmvc.GlobalCorsConfig;
import com.smm.frame.config.webmvc.GlobalReturnConfig;
import com.smm.frame.config.webmvc.GlobalWebConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Rao
 * @Date 2021/2/24
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {GlobalExceptionHandler.class, GlobalRedisConfig.class, RestTemplateAutoConfig.class, GlobalMybatisConfig.class,
        GlobalCorsConfig.class, GlobalReturnConfig.class, GlobalWebConfig.class})
public @interface EnableFrame {
}
