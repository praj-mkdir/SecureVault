package com.praj.secureVault.config.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private  final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private final JwtConverterProperties jwtConverterProperties;


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Collection<GrantedAuthority> authorities = Stream.concat(
//                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
//                extractResourceRoles(jwt).
//                        stream().
//                        collect(Collectors.toSet());
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).
                collect(Collectors.toSet());
                return new JwtAuthenticationToken(jwt, authorities,getPrincipalName(jwt));
    }

    private  String getPrincipalName(Jwt jwt){
        String claimName = JwtClaimNames.SUB;
        if(jwtConverterProperties.getPrincipalAttribute() !=null){
            claimName = jwtConverterProperties.getPrincipalAttribute();
        }
        return jwt.getClaim(claimName);
    }

    private Collection< ? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        Map<String,Object> resource;
        Collection<String> resourceRoles;

        if(resourceAccess == null
                || (resource = (Map<String, Object>) resourceAccess.get(jwtConverterProperties.getResourceId())) == null
                || (resourceRoles= (Collection<String>) resource.get("roles")) == null) {
            return Set.of();
        }

        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

}
