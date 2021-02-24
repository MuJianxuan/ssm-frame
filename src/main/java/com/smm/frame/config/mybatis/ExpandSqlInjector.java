package com.smm.frame.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @author Rao
 * @Date 2020/10/23
 * 扩展 基本的sql使用
 **/
public class ExpandSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methods = super.getMethodList(mapperClass);
        //添加批量插入的mapper方法
        methods.add(new InsertBatchSomeColumn());
        return methods;
    }
}
