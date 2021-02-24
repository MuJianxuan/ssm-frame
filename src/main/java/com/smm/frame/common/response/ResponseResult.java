package com.smm.frame.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianxuan
 * @since 2019/10/14 15:16
 * 注释: 统一数据返回封装类
 */
@Data
public class ResponseResult {
    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private int code;
    /**
     * 返回信息
     */
    @ApiModelProperty("信息")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty("数据")
    private Object data;

    @ApiModelProperty("分页数据")
    private PageData pageData;

    private ResponseResult(int code, String msg) {
        this(code, msg, null);
    }

    private ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMessage(), data);
    }

    public static ResponseResult success() {
        return new ResponseResult(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMessage(), null);
    }

    public static ResponseResult fail() {
        return new ResponseResult(ApiCode.FAIL.getCode(), ApiCode.FAIL.getMessage(), null);
    }

    public static ResponseResult info(Object data) {
        return new ResponseResult(ApiCode.SUCCESS_MESSAGE.getCode(), ApiCode.SUCCESS_MESSAGE.getMessage(), data);
    }

    public static ResponseResult authFail() {
        return new ResponseResult(ApiCode.FAIL_LOGIN.getCode(), ApiCode.FAIL_LOGIN.getMessage(), ApiCode.FAIL_LOGIN.getMessage());
    }
}
