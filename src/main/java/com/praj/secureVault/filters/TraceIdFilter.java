package com.praj.secureVault.filters;

import com.praj.secureVault.controller.UploadController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    private  static  final String TRACE_ID_HEADER = "X-Trace-Id";

//    private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String traceId = request.getHeader(TRACE_ID_HEADER);

        if(traceId == null || traceId.isBlank()){
            traceId = UUID.randomUUID().toString().replace("-","").substring(0,16);
            log.debug("Trace ID set for request [{}]: {}", request.getRequestURI(), traceId);

        }

        MDC.put("traceId", traceId);

        response.setHeader(TRACE_ID_HEADER, traceId);

        try {
            filterChain.doFilter(request,response);
        }
        finally { // this filter will run the last because , because this filter is set the highest priority,

            MDC.clear();
        }
    }
}
