package com.smm.frame.config.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @author Rao
 * @Date 2020/10/23
 * 强化
 **/
public interface IntensifyMapper<T> extends BaseMapper<T> {


    /**
     * 批量插入 仅适用于mysql
     * @param entityList 实体列表
     * @return 影响行数
     */
    int insertBatchSomeColumn(Collection<T> entityList);
}
