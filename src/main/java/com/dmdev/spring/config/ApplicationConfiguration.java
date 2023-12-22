package com.dmdev.spring.config;

import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;


//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration(proxyBeanMethods = true)// по умолчанию и так стоит true!
public class ApplicationConfiguration {

    @Bean("pool2") // можно не писать pool2, если назвать метод pool2!
    @Scope(BeanDefinition.SCOPE_SINGLETON) // не обязательно, по умолчанию стоит синглтон!
    public ConnectionPool pool2(@Value("${db.username}") String userName){
        return new ConnectionPool(userName, 20);
    }

    @Bean
    public ConnectionPool pool3(){
        return new ConnectionPool("test-pool", 25);
    }

   // @Profile("prod")
   // @Bean
   // public UserRepository userRepository2(ConnectionPool pool2){
   //     return new UserRepository((pool2));
   // }


   //@Bean
   //public UserRepository userRepository3(){
   //    var connectionPool1 = pool3();
   //    var connectionPool2 = pool3();
   //    var connectionPool3 = pool3();
   //    return new UserRepository(connectionPool3);
   //}
}
