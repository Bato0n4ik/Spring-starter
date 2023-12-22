package com.dmdev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

@Component
public class InjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware{
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var fields = bean.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(InjectBean.class))
                .forEach(field -> {
                    Object initBean = applicationContext.getBean(field.getType());
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, initBean);
                });

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
