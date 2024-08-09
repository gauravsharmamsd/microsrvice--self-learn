package com_employee_app.employee_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmployeeConfig {


    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(@Value("${address.base.url}") String url, RestTemplateBuilder builder) {


        //return builder.rootUri(url).build();
        return builder.build();
    }

    @Bean
    public WebClient getWebClient(@Value("${address.base.url}") String url) {
        //return WebClient.builder().baseUrl(url).build();
        return WebClient.builder().build();
    }


}
