package com.dmdev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class SecondAspect {

    @Around("com.dmdev.spring.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingInMethod(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.debug("AROUND before - invoked findById method in {} class with id {}", service, id);
        try{
            Object result = joinPoint.proceed();
            log.debug("AROUND after returning result - findById method in {} class with id {}", service, id);
            return result;
        }
        catch(Throwable exc){
            log.debug("AROUND- after Throwing error - findById method in {} class with id {}", service, id);
            throw exc;
        }  finally{
            log.debug("AROUND- after finally - invoked findById method in {} class with id {}", service, id);
        }
    }
}
