package com.smm.frame.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author jianxuan
 * @since 2019/10/14 14:50
 * 注释: 自定义controller
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validated
@RequestMapping
@RestController
public @interface PathRestController {

    /**
     * 对原注解进行重写
     */
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};


    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

}
