package com.dmdev.spring.integration.service;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;
    private final UserService userService;

    @Test
    void findAll(){
        var users = userService.findAll();
        Assertions.assertThat(users).hasSize(5);
    }

    @Test
    void findById(){
        var mayBeUser = userService.findById(USER_1);
        assertTrue(mayBeUser.isPresent());
        mayBeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create(){
        var userDto = new UserCreateEditDto("test@gmail.com",
        LocalDate.now(),
        "Test",
        "Test",
        Role.USER,
        COMPANY_1);

        var actualResult = userService.create(userDto);
        assertEquals(actualResult.getUsername(), userDto.getUsername());
        assertEquals(actualResult.getBirthDate(), userDto.getBirthDate());
        assertEquals(actualResult.getFirstname(), userDto.getFirstname());
        assertEquals(actualResult.getLastname(), userDto.getLastname());
        assertEquals(actualResult.getCompany().id(), userDto.getCompanyId());
        assertSame(actualResult.getRole(), userDto.getRole());
    }

    @Test
    void update(){
        var userDto = new UserCreateEditDto("test@gmail.com",
                LocalDate.now(),
                "Test",
                "Test",
                Role.USER,
                COMPANY_1);

        var actualResult = userService.update(USER_1, userDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user ->{
            assertEquals(user.getUsername(), userDto.getUsername());
            assertEquals(user.getBirthDate(), userDto.getBirthDate());
            assertEquals(user.getFirstname(), userDto.getFirstname());
            assertEquals(user.getLastname(), userDto.getLastname());
            assertEquals(user.getCompany().id(), userDto.getCompanyId());
            assertSame(user.getRole(), userDto.getRole());
        });

    }

    @Test
    void delete(){
        assertTrue(userService.delete(USER_1));
    }
}
