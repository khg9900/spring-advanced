package org.example.expert.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AdminLoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(org.example.expert.domain.common.annotation.Admin)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String requestBody = findRequestBody(joinPoint);


        log.info("User ID : {}, User Role : {}, Timestamp : {}, Request URL : {}, Request Body : {}",
                request.getAttribute("userId"),
                request.getAttribute("userRole"),
                LocalDateTime.now(),
                request.getRequestURI(),
                requestBody
        );

        Object result = joinPoint.proceed();
        log.info("Response Body : {}", objectMapper.writeValueAsString(result));

        return result;
    }

    public String findRequestBody (ProceedingJoinPoint joinPoint) throws JsonProcessingException {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();

        Object requestBody = null;
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof RequestBody) {
                    requestBody = joinPoint.getArgs()[i];
                }
            }
        }

        return objectMapper.writeValueAsString(requestBody);
    }
}
