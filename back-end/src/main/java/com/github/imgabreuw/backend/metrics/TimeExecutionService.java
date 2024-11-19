package com.github.imgabreuw.backend.metrics;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Log4j2
@Aspect
@Component
public class TimeExecutionService {

    @Around("@annotation(com.github.imgabreuw.backend.metrics.Timed)")
    public Object measureExecutionTimeWithAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.warn("Exception occurred while executing method: {}", joinPoint.getSignature().getName(), e);
            throw e;
        }

        long executionTime = System.nanoTime() - start;
        Duration duration = Duration.ofNanos(executionTime);
        log.info("Execution time of {}: {} milliseconds", joinPoint.getSignature().getName(), duration.toMillis());

        return result;
    }

}
