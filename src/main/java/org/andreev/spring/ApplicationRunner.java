package org.andreev.spring;

import org.andreev.spring.config.ApplicationConfiguration;
import org.andreev.spring.config.DatabaseProperties;
import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.service.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


import java.util.function.Supplier;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        System.out.println(context.getBeanDefinitionCount());
        System.out.println(context.getBean("pool1"));
        System.out.println(context.getBean(DatabaseProperties.class));
    }
}
