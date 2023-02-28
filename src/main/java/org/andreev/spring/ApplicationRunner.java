package org.andreev.spring;

import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.repository.UserRepository;
import org.andreev.spring.ioc.Container;
import org.andreev.spring.pool.ConnectionPool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {

        try(var context = new ClassPathXmlApplicationContext("application.xml")){
            System.out.println(context.getBean("pool1", ConnectionPool.class));
            var companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }

    }
}
