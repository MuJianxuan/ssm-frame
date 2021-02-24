package com.smm.frame.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Rao
 * @Date 2020/8/6
 **/
public class GlobalRedisConfig {


    /**
     * 配置Redis的序列化
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();

        //设置工厂
        redisTemplate.setConnectionFactory( lettuceConnectionFactory);
        //主要是设置key和value的序列化
        redisTemplate.setKeySerializer( keySerializer());
        redisTemplate.setValueSerializer( valueSerializer());

        redisTemplate.setHashKeySerializer( keySerializer());
        redisTemplate.setHashValueSerializer( valueSerializer());

        /*必须执行这个函数,初始化RedisTemplate*/
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**键序列化**/
    private StringRedisSerializer keySerializer(){
        return new StringRedisSerializer();
    }

    /**值序列化**/
    private Jackson2JsonRedisSerializer<Object> valueSerializer(){
        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        redisSerializer.setObjectMapper(objectMapper);
        return redisSerializer;
    }


}
