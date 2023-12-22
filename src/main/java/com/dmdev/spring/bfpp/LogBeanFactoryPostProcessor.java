package com.dmdev.spring.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       for(String beanDefinitionName : beanFactory.getBeanDefinitionNames()){
           var beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
           var genericArgumentValues = beanDefinition.getConstructorArgumentValues().getGenericArgumentValues();
           for(ConstructorArgumentValues.ValueHolder genericArgumentValue : genericArgumentValues){

           }

       }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
