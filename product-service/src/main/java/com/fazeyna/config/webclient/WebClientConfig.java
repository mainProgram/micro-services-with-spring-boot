package com.fazeyna.config.webclient;

import com.fazeyna.config.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient userWebClient(){
        return WebClient.builder()
                .baseUrl("http://auth-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public UserClient userClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(
                        WebClientAdapter.create(userWebClient())
                )
                .build();

        return httpServiceProxyFactory.createClient(UserClient.class);

    }
}
