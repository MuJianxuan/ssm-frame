package com.smm.frame.config.webmvc;

import com.smm.frame.common.response.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author jianxuan
 * @since 2019/10/14 15:45
 * 注释: 返回数据统一处理类 封装成ResponseResult对象
 * Controller返回参数全局包装成ResponseResult对象
 * 使用是一般需要指定basePackages，@RestControllerAdvice(basePackages = {"com.netx.web.controller"})
 * 只拦截controller包下的类；否则swagger也会拦截影响swagger正常使用
 */
@EnableWebMvc
@RestControllerAdvice
public class GlobalReturnConfig implements ResponseBodyAdvice<Object> {

    /**
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断支持的类型，因为我们定义的ResponseResult 里面的data可能是任何类型，这里就不判断统一放过
        //如果你想对执行的返回体进行操作，可将上方的Object换成你自己的类型
        return true;
    }

    /**
     * @param returnBody            返回的数据主体
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object returnBody, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //返回值为void
        if (returnBody == null) {
            return ResponseResult.success();
        }
        //如果返回的对象是数据封装类，说明已经被处理过（异常封装等情况）,则不需要进行打包
        if (returnBody instanceof ResponseResult) {
            return returnBody;
        }
        return ResponseResult.success(returnBody);
    }


}
