package com.dmdev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuditingBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> auditingBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Auditing.class)){
            auditingBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = auditingBeans.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    ((proxy, method, args) -> {
                        System.out.println("Audit method: " + method.getName());
                        var startTime = System.nanoTime();
                        try{
                            return method.invoke(bean, args);
                        }
                        finally{
                            System.out.println("Time execution : " +  (System.nanoTime() - startTime));
                        }
                    }));
        }
        return bean;
    }
}
