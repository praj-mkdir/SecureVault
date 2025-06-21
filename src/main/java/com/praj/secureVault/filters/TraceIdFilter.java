package com.praj.secureVault.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    private  static  final String TRACE_ID_HEADER = "X-Trace-Id";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String traceId = request.getHeader(TRACE_ID_HEADER);

        if(traceId == null || traceId.isBlank()){
            traceId = UUID.randomUUID().toString();
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
