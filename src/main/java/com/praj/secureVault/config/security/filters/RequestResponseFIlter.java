package com.praj.secureVault.config.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class RequestResponseFIlter extends OncePerRequestFilter {
    private static final Logger log
            = LoggerFactory.getLogger(RequestResponseFIlter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //this class helps wrap request and response to allow reading multiple times , normall response body can be read only oce
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(wrappedRequest,wrappedResponse);
        }finally {
            //Request metadata
            log.info("Method: {}, URI: {}", wrappedRequest.getMethod(), wrappedRequest.getRequestURI());

            // Log request
            //todo (prod)-> Change this funcitonality, write code for, only logging the metadata for prod
            log.info("Request URI: {}, Body: {}", request.getRequestURI(),
                    new String(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding()));

            log.info("Response Status: {}", wrappedResponse.getStatus());
            // Log response todo --> dont log in the prod profile. only metadata
            log.info("Response Status: {}, Body: {}", response.getStatus(),
                    new String(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding()));


            wrappedResponse.copyBodyToResponse();


        }
    }
}
