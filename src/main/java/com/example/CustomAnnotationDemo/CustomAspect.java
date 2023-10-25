package com.example.CustomAnnotationDemo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomAspect {

    @Autowired
    private HttpServletRequest request;

    private RequestLogger requestLogger;

    @Around("@annotation(RequestLogger)")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!requestLogger.enabled()){
            log.warn("{} : Request received but logging is disabled", request.getRequestURI());
        }
        log.info("{} : Request received", request.getRequestURI());
        Object obj = joinPoint.proceed();
        log.info("{} : Request finished", request.getRequestURI());
        return obj;
    }
}
