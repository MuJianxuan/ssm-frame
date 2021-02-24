package com.smm.frame.common.utils;

import com.smm.frame.config.exception.ServiceException;


/**
 * 断言工具类
 * 区别于普通断言类，这个类专用与抛出业务异常
 * @author Rao
 */
public class AssertX {

    /**
     * 断言能找到 否则抛出message自定义信息
     * @param res
     * @param message
     */
    public static void found(Object res, String message) {
        if (res == null) {
            throw new ServiceException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }
}
