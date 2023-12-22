package com.dmdev.spring.integration.service;


import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;


//@SpringBootTest
@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;
    private final ConnectionPool pool1;

    @Test
    void test1(){
        System.out.println();
    }

    @Test
    void test2(){
        System.out.println();
    }
}
