package com.syf.paylater.data.store.config.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Clears per-thread logging state (MDC + logstash ThreadLocal)
 * after every request to prevent memory retention on Tomcat worker threads.
 */
@Component
@Order(Integer.MIN_VALUE) // run very early in the filter chain
public class LoggingContextCleanupFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } finally {
            // Always clear MDC at end of request
            try {
                MDC.clear();
            } catch (Throwable ignore) {}

            // Clear net.logstash.logback ThreadLocalHolder that MAT showed retaining buffers
            try {
                Class<?> holder = Class.forName("net.logstash.logback.util.ThreadLocalHolder");
                holder.getMethod("clear").invoke(null); // static clear()
            } catch (Throwable ignore) {
                // Safe no-op if class signature differs
            }
        }
    }
}
