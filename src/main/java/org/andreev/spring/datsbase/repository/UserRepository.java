package org.andreev.spring.datsbase.repository;

import org.andreev.spring.datsbase.pool.ConnectionPool;

public class UserRepository {
    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
}
