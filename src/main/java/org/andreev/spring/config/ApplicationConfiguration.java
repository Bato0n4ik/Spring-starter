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
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "org.andreev.spring",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION, value=Component.class),
                @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @ComponentScan.Filter(type=FilterType.REGEX, pattern = "org\\..+Repository")
        }
)
public class ApplicationConfiguration {
        @Bean
        public ConnectionPool pool2(@Value ("${db.pool.size}") Integer poolSize,
                                    @Value("${db.db.username}") String userName){
                return new ConnectionPool(poolSize, userName);
        }

        @Bean
        public ConnectionPool pool3(){
                return new ConnectionPool(20, "test-pool");
        }

        @Bean
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public UserRepository userRepository2(ConnectionPool pool2){
                return new UserRepository(pool2);
        }

        @Bean
        @Profile("prod")
        public UserRepository userRepository3(){
                return new UserRepository(pool3());
        }
}
