package com.api.tuctapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${api.public-url}")
    private String apiPublicUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .servers(java.util.List.of(
                        new Server()
                                .url(apiPublicUrl)
                                .description("Servidor da API")
                ))
                .info(new Info()
                        .title("Tuct API")
                        .version("1.0")
                        .description("API para gerenciamento de giras"))
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}