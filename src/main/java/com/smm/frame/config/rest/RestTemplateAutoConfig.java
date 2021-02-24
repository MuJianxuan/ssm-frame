package com.smm.frame.config.rest;

/**
 * @author Rao
 * @Date 2020/8/16
 **/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 远程调用rest接口客户端注册
 **/
public class RestTemplateAutoConfig {
    /**
     * 连接超时时间 (毫秒)
     */
    private static final Integer CONNECTION_TIMEOUT = 6000;
    /**
     * 信息读取超时时间 （毫秒）
     */
    private static final Integer READ_TIMEOUT = 6000;

    /**
     * 功能描述：注册restTemplate服务
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/6 20:26
     * @since v1.0
     **/

    @Bean
    public RestTemplate registerTemplate() {
        RestTemplate restTemplate = new RestTemplate(getFactory());
        //这个地方需要配置消息转换器，不然收到消息后转换会出现异常
        restTemplate.setMessageConverters(getConverts());
        return restTemplate;
    }


    /**
     * 功能描述： 初始化请求工厂
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/6 20:27
     * @since v1.0
     **/
    private SimpleClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECTION_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return factory;
    }

    /**
     * 功能描述：  设置数据转换器，我再这里只设置了String转换器
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/6 20:32
     * @since v1.0
     **/
    private List<HttpMessageConverter<?>> getConverts() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // String转换器
        StringHttpMessageConverter stringConvert = new StringHttpMessageConverter();
        List<MediaType> stringMediaTypes = new ArrayList<MediaType>() {{
            //配置text/plain和text/html类型的数据都转成String
            add(new MediaType("text", "plain", StandardCharsets.UTF_8));
            add(MediaType.TEXT_HTML);
        }};
        stringConvert.setSupportedMediaTypes(stringMediaTypes);
        messageConverters.add(stringConvert);
        return messageConverters;
    }
}

