package com.smm.frame.config.exception;

import com.smm.frame.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author jianxuan
 * @since 2019/10/14 16:18
 * 注释: 全局异常拦截器
 */

@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 这里这么理解：将exception发生的异常全局拦截处理，进行数据封装，使得信息更容易理解
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception ex) {
        log.error("系统发生异常:" + ex.getMessage(), ex);
        return ResponseResult.fail();
    }

    /**
     * 将自定义service异常类接收异常进行数据处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseResult serviceExceptionHandler(ServiceException ex) {
        log.error("系统发生异常:" + ex.getMessage(), ex);
        return ResponseResult.fail();
    }


    /**
     * validator校验信息失败异常进行数据处理（验证异常建议提示）
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult validationExceptionHandler(MethodArgumentNotValidException ex) {
        return ResponseResult.info(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }
}
