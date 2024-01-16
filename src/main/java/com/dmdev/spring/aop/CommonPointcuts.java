package com.dmdev.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    @Pointcut(value = "@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer(){
    }

    @Pointcut(value="within(com.dmdev.spring.service.*Service)")
    public void isServiceLayer(){
    }
}
