package org.andreev.spring;

import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {

        try(var context = new ClassPathXmlApplicationContext("application.xml")){
            System.out.println(context.getBean("pool1", ConnectionPool.class));
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1).orElseThrow(()-> "some type of error!"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
