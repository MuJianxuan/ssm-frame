package com.smm.frame.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jianxuan
 * @since 2019/10/14 16:07
 * 注释: 全局mybatis配置类
 */
@EnableTransactionManagement
public class GlobalMybatisConfig {

    /**
     * 配置解析枚举（如何序列化枚举值为数据库存储值？）
     * https://baomidou.com/guide/enum.html#jackson
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }


    /**
     * mybatis分页
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(500L);
        //添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        //添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防止全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


    /**
     * 注入公共字段（除了注入公共字段还可以使用自动填充）
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new GlobalMetaObjectHandler();
    }

    /**
     * 扩展基础的sql功能
     * @return
     */
    @Bean
    public ExpandSqlInjector expandSqlInjector(){
        return new ExpandSqlInjector();
    }

}
