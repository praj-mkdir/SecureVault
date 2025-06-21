package com.praj.secureVault.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI secureVaultConfig(){
        return new OpenAPI().info(
                new Info().title("SecureVault")
                        .description("Documentation for SecureVault App API's by Prajwal")
                        .version("v1")
        )
                //Here add the list of servers, change accordingly
                .servers(List.of(new Server().url("http://localhost:8081").description("local-dev")))
                .tags(List.of(
                        new Tag().name("Public API's"),
                        new Tag().name("File API's")
                )); //here add tag ordering for the documentation
    }
}
