package org.andreev.spring.pool;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

public class ConnectionPool implements InitializingBean {
    private final Integer poolSize;
    private final String userName;
    private final List<Object> args;
    //private final Map<String, Object> properties;
    private Map<String, Object> properties;

    public ConnectionPool(Integer poolSize, String userName, List<Object> args, Map<String, Object> properties) {
        this.poolSize = poolSize;
        this.userName = userName;
        this.args = args;
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties){
        this.properties = properties;
    }

    private void init() {
        System.out.println("Properties set");
    }

    private void destroy() {
        System.out.println("Clean connection pool!");
    }

    @Override                  // одно и тоже, что init, только реализуем функционал интерфейса
    public void afterPropertiesSet() throws Exception {
        System.out.println("Properties set");
    }

}
