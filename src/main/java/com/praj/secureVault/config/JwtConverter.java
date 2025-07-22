package com.praj.secureVault.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//custom jwt converter that combines the default 'scope' authorities with keycloak style resource roles
//        from resource_access
//converts JWT to springsecurity's JwtAuthenticationToken
// Converts JWT into Spring Security's JwtAuthenticationToken with:
//- Combined authorities from standard scopes and Keycloak roles
//- Principal name configurable via application properties


@RequiredArgsConstructor
@Slf4j
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

//    private static final Logger log = LoggerFactory.getLogger(JwtConverter.class);
    private  final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private final JwtConverterProperties jwtConverterProperties;


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        //

        Collection<GrantedAuthority> authorities = Stream.concat(
                //step 1:  default authorities from 'scope' claim (e.g. SCOPE_email, SCOPE_profile)
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                //step 2: Get custom roles from 'resource_access.secure-vault.roles'
                extractResourceRoles(jwt).stream()).
                //step 3:  Combine both sets of authorities
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

    // This method pulls roles from the Keycloak-style structure
    private Collection< ? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        // step1 : Get "resource_access" claim from the JWT
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//        log.info(resourceAccess.toString());
        // If it's missing, return empty
        if (resourceAccess == null) {
//            log.warn("No resource_access claim found in token.");
            return Collections.emptySet();
        }

        // 2. Get the client-specific resource block (e.g., for "secure-vault")
        String clientId = jwtConverterProperties.getResourceId(); // e.g. "secure-vault"
//        log.info("clientid {}",clientId ); //clientId
        Object resourceBlock = resourceAccess.get(clientId);

        if (resourceBlock == null || !(resourceBlock instanceof Map)) {
//            log.warn("No resource block found for client '{}'", clientId);
            return Collections.emptySet();
        }

        Map<String, Object> resource = (Map<String, Object>) resourceBlock;
//        log.info("resource_access.{} = {}", clientId, resource);
        // 3. Get the "roles" list inside the client block
        Object rolesObj = resource.get("roles");

        if (!(rolesObj instanceof Collection)) {
//            log.warn("No roles found for client '{}'", clientId);
            return Collections.emptySet();
        }

        Collection<String> roles = (Collection<String>) rolesObj;
//        log.info("Extracted roles for '{}': {}", clientId, roles);

        // 4. Convert roles into Spring Security format: "ROLE_user"
        return roles.stream()
                .map(role -> {
                    String authority = "ROLE_" + role;
                    //log.debug("Converting role '{}' to authority '{}'", role, authority);
                    return new SimpleGrantedAuthority(authority);
                })
                .collect(Collectors.toSet());
    }

}
