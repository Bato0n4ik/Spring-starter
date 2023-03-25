package org.andreev.spring.datsbase.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("pool1")
public class ConnectionPool{
    private final Integer poolSize;
    private final String userName;
    //private List<Object> args;

    //private Map<String, Object> properties;


    public ConnectionPool(@Value("${db.pool.size}") Integer poolSize, @Value("${db.username}")String userName) {
        this.poolSize = poolSize;
        this.userName = userName;
    }

    @PostConstruct
    private void init() {
        System.out.println("Properties set");
    }

    @PreDestroy
    private void destroy() {
        System.out.println("Clean connection pool!");
    }


}
