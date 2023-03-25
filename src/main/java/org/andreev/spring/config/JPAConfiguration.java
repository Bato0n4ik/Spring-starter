package org.andreev.spring.config;

import jakarta.annotation.PostConstruct;
import org.andreev.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(JpaCondition.class)
@Configuration
public class JPAConfiguration {

    @PostConstruct
    public void init(){
        System.out.println("Jpa configuration is enabled!");
    }
}
