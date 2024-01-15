package com.dmdev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Slf4j
@Component
public class FirstAspect {

    @Pointcut(value = "@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer(){
    }

    @Pointcut(value="within(com.dmdev.spring.service.*Service)")
    public void isServiceLayer(){
    }

    /*
        this - check AOP proxy class type
        target - check target object class type
    */
    @Pointcut("this(org.springframework.data.repository.Repository)")
    //@Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void isMethodLayer(){
    }

    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam(){
    }

    @Pointcut("isControllerLayer() && @args(com.dmdev.spring.validation.UserInfo, ..)")
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
}
