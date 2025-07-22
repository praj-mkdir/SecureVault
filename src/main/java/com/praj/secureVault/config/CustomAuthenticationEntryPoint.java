package com.praj.secureVault.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.praj.secureVault.util.response.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String traceId = MDC.get("traceId");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ApiErrorResponse error = new ApiErrorResponse.Builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(authException.getMessage())
                .timestamp(Instant.now())
                .traceId(traceId)
                .path(request.getRequestURI())
                .build();

        log.error("Unauthorized [{}]: {}", traceId, authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");



        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
