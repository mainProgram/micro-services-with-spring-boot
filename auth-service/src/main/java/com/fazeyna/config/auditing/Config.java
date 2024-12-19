package com.fazeyna.config.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class Config {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // our implementation of AuditorAware
        return new AuditorAwareImpl();
    }

}
