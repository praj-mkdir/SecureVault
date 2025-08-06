package com.praj.secureVault.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AuthUtil {

    private static final Pattern REALM_PATTERN = Pattern.compile(".*/realms/([^/]+)");

    public String getCurrentUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonymous";
        }
        return authentication.getName();
    }

    private Jwt getCurrentJwt() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof JwtAuthenticationToken jwtauth) {
                return jwtauth.getToken();
            }
            log.warn("Current authentication is not JwtAuthenticationToken");
            return null;
        } catch (Exception e) {
            log.error("Error getting current Jwt token", e);
            return null;
        }
    }

    public String getRealmName() {
        Jwt jwt = getCurrentJwt();
        if (jwt == null) {
            log.warn("JWT token is null");
            return null;
        }
        String issuer = jwt.getClaim(JwtClaimNames.ISS);
        if (issuer == null || issuer.isEmpty()) {
            log.warn("No issuer claim found in JWT");
            return null;
        }

        log.debug("Extracting realm from issuer: {}", issuer);

        Matcher matcher = REALM_PATTERN.matcher(issuer);
        if (matcher.find()) {
            String realmName = matcher.group(1);
            log.debug("Extracted realm name: {}", realmName);
            return realmName;
        }
        log.warn("Could not extract realm name from issuer: {}", issuer);
        return null;
    }
}
