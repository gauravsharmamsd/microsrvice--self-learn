package com.address__app.address_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
