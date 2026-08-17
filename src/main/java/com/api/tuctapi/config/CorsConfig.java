package com.api.tuctapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:4200",
                                "http://localhost:3000"
                        )
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders(
                                "Origin",
                                "Content-Type",
                                "Accept",
                                "Authorization",
                                "X-Requested-With"
                        )
                        .exposedHeaders(
                                "X-API-Version",
                                "X-Total-Count"
                        )
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}