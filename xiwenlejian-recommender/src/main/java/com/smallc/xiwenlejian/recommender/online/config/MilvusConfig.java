package com.smallc.xiwenlejian.recommender.online.config;

import io.milvus.client.ConnectParam;
import io.milvus.client.MilvusClient;
import io.milvus.client.MilvusGrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/24
 * @since com.smallc.xiwenlejian.recommender.online.config
 */
@Configuration
public class MilvusConfig {

    @Value("${milvus.host}")
    private String host;
    @Value("${milvus.port}")
    private int port;

    @Bean
    public MilvusClient milvusClient() {
        ConnectParam connectParam = new ConnectParam.Builder().withHost(host).withPort(port).build();
        MilvusClient client = new MilvusGrpcClient(connectParam);
        return client;
    }

}
