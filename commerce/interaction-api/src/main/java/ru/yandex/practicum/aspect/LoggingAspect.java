package ru.yandex.practicum.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для автоматического логирования методов, помеченных @Loggable
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(ru.yandex.practicum.annotation.Loggable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering method: {}", joinPoint.getSignature());

        Object[] args = joinPoint.getArgs();
        log.info("Request Parameters: {}", args);

        Object result = joinPoint.proceed(); // Выполнение самого метода

        log.info("Exiting method: {} - Response: {}", joinPoint.getSignature(), result);

        return result;
    }
}
