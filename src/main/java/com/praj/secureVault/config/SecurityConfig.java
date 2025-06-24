package com.praj.secureVault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static  final String USER = "user";
//    private final JwtConverter jwtConverter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        return new JwtGrantedAuthoritiesConverter();
    }

    @Bean
    public JwtConverter jwtConverter(
            JwtConverterProperties jwtConverterProperties,
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
        return new JwtConverter( jwtGrantedAuthoritiesConverter,jwtConverterProperties);
    }



    /// also create the Securityfilterchain based on the profile , one for production and one for dev
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtConverter jwtConverter) throws  Exception{
        http.authorizeHttpRequests((auth)->
                auth.requestMatchers(HttpMethod.GET, "/api/public/**", "/docs/**").permitAll()
                        .anyRequest().authenticated()

        )
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
//                ).csrf(csrf->csrf.disable());
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConverter)
                        )
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return http.build();
    }
}
