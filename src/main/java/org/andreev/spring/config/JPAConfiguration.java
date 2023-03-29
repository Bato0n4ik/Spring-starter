package org.andreev.spring.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.andreev.spring.config.condition.JpaCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JPAConfiguration {

    @PostConstruct
    public void init(){
        log.warn("Jpa configuration is enabled!");
    }

    /*
    @Bean
    @ConfigurationProperties(prefix = "db")
    public DatabaseProperties databaseProperties(){
        return new DatabaseProperties();
    }
    */
}
