package com.dmdev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Slf4j
@Component
@Order(1)
public class FirstAspect {



    /*
        this - check AOP proxy class type
        target - check target object class type
    */
    @Pointcut("this(org.springframework.data.repository.Repository)")
    //@Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){
    }


    @Pointcut("com.dmdev.spring.aop.CommonPointcuts.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void isMethodLayer(){
    }

    @Pointcut("com.dmdev.spring.aop.CommonPointcuts.isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam(){
    }

    @Pointcut("com.dmdev.spring.aop.CommonPointcuts.isControllerLayer() && @args(com.dmdev.spring.validation.UserInfo, ..)")
    public void hasUserInfoParamAnnotation(){
    }

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean(){
    }

    @Pointcut("execution(public * com.dmdev.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod(){
    }


    //this is Advice!
    //снизу, закоментированная строка необязательная, для более ранних версий java, в новых нет необходимости в ней!
    //она нужна, чтобы в runtime имена параметров запоминались JVM(или типо)!
    @Before(value = "anyFindByIdServiceMethod()" +
            " && args(id)" +
            " && target(service)" +
            " && this(serviceProxy)" +
            " && @within(transactional)"/*, argNames = "joinPoint,id,service,serviceProxy,transactional"*/)
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional){
        log.debug("invoked findById method in {} class with id {}", service, id);

    }

    @AfterReturning(value = "anyFindByIdServiceMethod() && target(service)", returning = "result")
    public void addLoggingAfterMethodResult(Object result, Object service){
        log.debug("after returning - invoked findById method in {} class with result: {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() && target(service)", throwing = "exc")
    public void addLoggingAfterThrowing(Throwable exc, Object service){
        log.debug("after throwing - invoked findById method in class {}, exception class: {}, message exception: {}", service,exc.getMessage(), exc.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingFinally(Object service){
        log.debug("after (finally) - invoked findById method in class {}", service);
    }
}
