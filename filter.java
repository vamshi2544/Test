package com.syf.paylater.config.logging;

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
@Order(Integer.MIN_VALUE) // run as early as possible in the chain
public class LoggingContextCleanupFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain)
      throws ServletException, IOException {
    try {
      chain.doFilter(request, response);
    } finally {
      // 1) Clear your MDC (you already do this in methods; this is belt-and-suspenders)
      try { MDC.clear(); } catch (Throwable ignore) {}

      // 2) Clear the EXACT ThreadLocal holder MAT showed:
      //    net.logstash.logback.util.ThreadLocalHolder
      try {
        Class<?> holder = Class.forName("net.logstash.logback.util.ThreadLocalHolder");
        holder.getMethod("clear").invoke(null); // static clear()
      } catch (Throwable ignore) {
        // If class/signature differs on your version, this safely no-ops.
      }
    }
  }
}
