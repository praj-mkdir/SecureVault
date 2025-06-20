package com.praj.secureVault.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI secureVaultConfig(){
        return new OpenAPI().info(
                new Info().title("SecureVault")
                        .description("Documentation for SecureVault App API's by Prajwal")
                        .version("v1")
        );
    }
}
