package org.andreev.spring.config;

import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.datsbase.repository.UserRepository;
import org.andreev.spring.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Import(WebConfiguration.class)
@Configuration
public class ApplicationConfiguration {

        @Bean
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public ConnectionPool pool2(/*@Value("${db.db.username}") String userName*/){
                return new ConnectionPool(/*userName*/"pool2", 20);
        }

        @Bean
        public ConnectionPool pool3(){
                return new ConnectionPool("test-pool", 20);
        }

        @Bean
        @Profile("prod|web")
        public UserRepository userRepository2(ConnectionPool pool2){
                return new UserRepository(pool2);
        }

        @Bean
        public UserRepository userRepository3(){
                return new UserRepository(pool3());
        }
}
