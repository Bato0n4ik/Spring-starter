package org.andreev.spring.datsbase.repository;

import org.andreev.spring.pool.ConnectionPool;

public class CompanyRepository {
    private final  ConnectionPool connectionPool;

    public CompanyRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static CompanyRepository of(ConnectionPool connectionPool){
        return new CompanyRepository(connectionPool);
    }
}
