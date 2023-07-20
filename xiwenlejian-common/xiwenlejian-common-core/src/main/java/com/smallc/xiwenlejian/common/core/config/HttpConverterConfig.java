package com.smallc.xiwenlejian.common.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * fastjson配置
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.common.core.config
 */
@Configuration
public class HttpConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1、定义一个converters转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2、添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 3、在converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4、设置返回的编码
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 5、将converter赋值给HttpMessageConverter
        HttpMessageConverter<?> converter = fastConverter;
        // 6、返回HttpMessageConverters对象
        return new HttpMessageConverters(converter);
    }

}