package com.dmdev.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor
public class ConnectionPool {

    @Value("${db.username}")
    private final  String username;

    @Value("${db.pool.size}")
    private final  Integer poolSize;

    @PostConstruct
    public void init(){
        log.info("init beans");
    }

    @PreDestroy
    public void destroy(){
        log.info("clean connection pool!");
    }

    //private  List<Object> args;
    //private Map<String, Object> properties;

    /*
    public ConnectionPool(String username, Integer poolSize, List<Object> args, Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }*/

}
