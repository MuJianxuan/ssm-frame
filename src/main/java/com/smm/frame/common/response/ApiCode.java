package com.smm.frame.common.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author jianxuan
 * @since 2019/9/20 15:02
 * 注释: api接口返回代码表
 */
@Getter
public enum ApiCode {

    /**
     * 枚举信息
     */
    SUCCESS(200) {
        @Override
        public String getMessage() {
            return "操作成功！";
        }
    },
    SUCCESS_MESSAGE(201) {
        @Override
        public String getMessage() {
            return "请求成功但需提醒";
        }
    },
    FAIL(500) {
        @Override
        public String getMessage() {
            return "系统繁忙，请稍后重试！";
        }
    },
    FAIL_LOGIN(501) {
        @Override
        public String getMessage() {
            return "授权过期，请重新登录！";
        }
    },
    VALIDA_ERROR(400){
        @Override
        public String getMessage() {
            return "验证失败提醒";
        }
    },
    NO_PERMISSION(402){
        @Override
        public String getMessage() {
            return "无权限操作！";
        }
    };

    ApiCode(Integer code) {
        this.code = code;
    }

    /**
     * 抽象一个方法
     *
     * @return
     */
    public abstract String getMessage();

    @JsonValue
    private final Integer code;

    /**
     * 判断是否存在对应code的枚举
     *
     * @param code
     * @return
     */
    public static ApiCode getTarget(int code) {
        for (ApiCode apiCode : ApiCode.values()) {
            if (apiCode.getCode() == code) {
                return apiCode;
            }
        }
        return null;
    }

}
