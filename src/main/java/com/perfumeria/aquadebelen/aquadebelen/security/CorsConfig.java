package com.perfumeria.aquadebelen.aquadebelen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                            "http://localhost:3000",     // React
                            "http://localhost:5173",   
                            "http://localhost:5174",  // Vite
                            "http://localhost:4200",     // Angular
                            "http://localhost:8081",     // Vue
                            "https://aquadebelenfront.prod.dtt.tja.ucb.edu.bo"  // Frontend en producción
                        )
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
            
        };
    }

}
