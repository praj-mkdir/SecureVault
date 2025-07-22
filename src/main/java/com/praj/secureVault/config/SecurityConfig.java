package com.praj.secureVault.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
//    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

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
        return new JwtConverter(jwtGrantedAuthoritiesConverter, jwtConverterProperties);
    }


    /// also create the Securityfilterchain based on the profile , one for production and one for dev
    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http, JwtConverter jwtConverter) throws Exception {
        log.info("Inside the dev devSecurityFilterChain ");
        http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) ->
                                auth
//                        .requestMatchers(HttpMethod.GET, "/api/v1/public/**", "/docs/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/public/**", "/docs/**").permitAll()

                                        .anyRequest().permitAll()

                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConverter)
                        )
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return http.build();
    }

    @Bean
    @Profile("prod") //Add stricter security features - here
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http, JwtConverter jwtConverter) throws Exception {
        log.info("Inside the prod prodSecurityFilterChain ");
        http.authorizeHttpRequests((auth) ->
                        auth.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConverter)
                        )
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return http.build();
    }
}
