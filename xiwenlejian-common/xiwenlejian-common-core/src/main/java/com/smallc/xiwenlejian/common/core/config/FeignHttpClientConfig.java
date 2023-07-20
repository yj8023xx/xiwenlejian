package com.smallc.xiwenlejian.common.core.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.common.core.config
 */
@Configuration
public class FeignHttpClientConfig {

    @Bean(destroyMethod = "close")
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(100);

        RequestConfig requestConfig = RequestConfig.custom()
                // 从连接池获取连接等待超时时间
                .setConnectionRequestTimeout(2000)
                // 请求超时时间
                .setConnectTimeout(2000)
                // 等待服务响应超时时间
                .setSocketTimeout(15000)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictExpiredConnections();
        return httpClientBuilder.build();
    }

}
