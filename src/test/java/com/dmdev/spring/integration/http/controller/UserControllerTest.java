package com.dmdev.spring.integration.http.controller;

import com.dmdev.spring.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.bouncycastle.cert.ocsp.Req;
import org.testcontainers.shaded.org.hamcrest.collection.IsCollectionWithSize;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest {

    private final MockMvc mvc;

    @Test
    void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", IsCollectionWithSize.hasSize(5)));
    }

    @Test
    void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .param(UserCreateEditDto.Fields.username, "test@gmail.com")
                .param(UserCreateEditDto.Fields.firstname, "test")
                .param(UserCreateEditDto.Fields.lastname, "test")
                .param(UserCreateEditDto.Fields.role, "ADMIN")
                .param(UserCreateEditDto.Fields.companyId, "1")
                .param(UserCreateEditDto.Fields.birthDate, "2000-10-11")// нужен конвертер для даты!
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().is3xxRedirection(),
                        MockMvcResultMatchers.redirectedUrlPattern("/users/{\\d+}")
                );
    }
}