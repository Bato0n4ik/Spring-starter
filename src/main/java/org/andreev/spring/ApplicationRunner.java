package org.andreev.spring;

import org.andreev.spring.config.ApplicationConfiguration;
import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.service.CompanyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.function.Supplier;

public class ApplicationRunner {
    public static void main(String[] args) {

        try(/*var context = new ClassPathXmlApplicationContext("application.xml")*/
                var context = new AnnotationConfigApplicationContext()){
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web", "prod");
            context.refresh();

            System.out.println(context.getBean("pool1", ConnectionPool.class));
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
            //var companyService = context.getBean(CompanyService.class);
            //System.out.println(companyService.findById(1).orElse(null));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
