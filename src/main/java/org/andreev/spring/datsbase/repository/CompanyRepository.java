package org.andreev.spring.datsbase.repository;

import jakarta.annotation.PostConstruct;
import org.andreev.spring.bpp.Auditing;
import org.andreev.spring.bpp.InjectBean;
import org.andreev.spring.bpp.Transaction;
import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Scope(BeanDefinition.SCOPE_SINGLETON)
@Auditing
@Transaction
@Repository
public class CompanyRepository implements CrudRepository<Integer, Company>{

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    private final int poolSize;

    public CompanyRepository(ConnectionPool pool1, List<ConnectionPool> pools, @Value("${db.pool.size}") int poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

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
