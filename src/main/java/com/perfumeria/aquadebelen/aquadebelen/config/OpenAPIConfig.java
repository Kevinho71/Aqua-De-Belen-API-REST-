package com.perfumeria.aquadebelen.aquadebelen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI aquaDeBelenOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Aqua de Belén")
                        .version("1.0.0")
                        .description("Sistema de gestión de inventario y ventas para perfumería Aqua de Belén"));
    }
}
