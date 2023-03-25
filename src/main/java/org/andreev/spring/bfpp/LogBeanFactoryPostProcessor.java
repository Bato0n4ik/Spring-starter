package org.andreev.spring.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for(String beanDefNames: beanFactory.getBeanDefinitionNames()){
            var beanDefinition = beanFactory.getBeanDefinition(beanDefNames);
            beanDefinition.getConstructorArgumentValues().getIndexedArgumentValues().values()
                    .stream().forEach(x->x.getValue().toString());
        }
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
