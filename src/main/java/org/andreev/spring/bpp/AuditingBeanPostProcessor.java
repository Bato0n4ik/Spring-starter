package org.andreev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuditingBeanPostProcessor implements BeanPostProcessor {

    Map<String, Class<?>> auditBean = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Auditing.class)){
            auditBean.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = auditBean.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("Audit transaction");
                        var startTime = System.nanoTime();
                        try{
                            return method.invoke(bean,args);
                        }
                        finally {
                            System.out.println("Time execution: " + (System.nanoTime() - startTime));
                        }
                    } );
        }
        return bean;
    }
}
