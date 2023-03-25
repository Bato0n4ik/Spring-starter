package org.andreev.spring.datsbase.repository;

import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Scope(BeanDefinition.SCOPE_SINGLETON)
@Repository
public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(@Qualifier("pool2") ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
}
