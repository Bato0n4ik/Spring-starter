package org.andreev.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.andreev.spring.datsbase.pool.ConnectionPool;
import org.andreev.spring.integration.annotation.IT;
import org.andreev.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

@IT
@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT {


    private final UserService userService;
    private final ConnectionPool pool1;

    @Test
    void test(){
        System.out.println("UserServiceIT test!");
    }

    @Test
    void test2(){
        System.out.println("UserServiceIT test2!");
    }
}
