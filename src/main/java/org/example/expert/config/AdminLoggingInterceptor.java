package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Slf4j
public class AdminLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LocalDateTime now = LocalDateTime.now();

        log.info("User ID : {}, User Role : {}, Request URL : {}, Timestamp : {}",
                request.getAttribute("userId"),
                request.getAttribute("userRole"),
                request.getRequestURI(),
                now);
        return true;
    }
}
