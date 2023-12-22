package com.dmdev.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Data
//@NoArgsConstructor
//@ConstructorBinding
@ConfigurationProperties(prefix = "db")
public record DatabaseProperties(
    String username,
    String password,
    String driver,
    String url,
    String hosts,
    Map<String, Object> properties,
    PoolProperties pool,
    List<PoolProperties> pools){

    //@Data
    //@NoArgsConstructor
    //@ConstructorBinding
    public static record PoolProperties(
            Integer size,
            Integer timeout){

    }
}
