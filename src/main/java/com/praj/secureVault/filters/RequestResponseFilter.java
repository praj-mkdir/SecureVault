package com.praj.secureVault.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RequestResponseFilter extends OncePerRequestFilter {

    @Value("${spring.profiles.active:}")
    private String activeProfile;



//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String uri = request.getRequestURI();
//        if (uri.startsWith("/api/v1/file/download")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //this class helps wrap request and response to allow reading multiple times , normall response body can be read only oce
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//        try {
//            filterChain.doFilter(wrappedRequest, wrappedResponse);
//        } finally {
//            //why in finally ? Finally runs after controller finishes before the response is sent back, to log everything going in and going out.
//            //Request metadata
//            log.info("Method: {}, URI: {}", wrappedRequest.getMethod(), wrappedRequest.getRequestURI());
//
//            // Log request
//            //todo (prod)-> Change this funcitonality, write code for, only logging the metadata for prod
//            if ("dev".equalsIgnoreCase(activeProfile)) {
//                String requestBody = getRequestBody(wrappedRequest);
//                String responseBody = getResponseBody(wrappedResponse);
//
//                log.info("Request Body: {}", requestBody);
//                log.info("Response Body: {}", responseBody);
//            }
//
//
//            log.info("Response Status: {}", wrappedResponse.getStatus());
//
//
//            wrappedResponse.copyBodyToResponse();
//
//
//        }
//    }
//
//    private String getRequestBody(ContentCachingRequestWrapper request) {
//        byte[] buf = request.getContentAsByteArray();
//        if (buf.length == 0) return "[empty]";
//        try {
//            return new String(buf, request.getCharacterEncoding());
//        } catch (UnsupportedEncodingException ex) {
//            return "[unknown encoding]";
//        }
//    }
//
//    private String getResponseBody(ContentCachingResponseWrapper response) {
//        byte[] buf = response.getContentAsByteArray();
//        if (buf.length == 0) return "[empty]";
//        try {
//            return new String(buf, response.getCharacterEncoding());
//        } catch (UnsupportedEncodingException ex) {
//            return "[unknown encoding]";
//        }
//    }



//    private static final Logger log = LoggerFactory.getLogger(RequestResponseFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            log.info("Request - Method: {}, URI: {}, Status: {}, Duration: {} ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);

            String traceId = MDC.get("traceId");
            if (traceId != null) {
                log.info("Trace ID: {}", traceId);
            }

            if ("dev".equalsIgnoreCase(activeProfile)) {
                log.debug("Headers: {}", Collections.list(request.getHeaderNames()).stream()
                        .collect(Collectors.toMap(h -> h, request::getHeader)));

                log.debug("Query Params: {}", request.getQueryString());
            }
        }
    }
}
