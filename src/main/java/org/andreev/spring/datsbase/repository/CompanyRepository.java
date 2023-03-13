package org.andreev.spring.datsbase.repository;

import jakarta.annotation.PostConstruct;
import org.andreev.spring.bpp.Auditing;
import org.andreev.spring.bpp.InjectBean;
import org.andreev.spring.bpp.Transaction;
import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.pool.ConnectionPool;

import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company>{

    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    private void init(){
        System.out.println("init company repository!");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method");
    }
}
