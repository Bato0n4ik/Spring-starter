package com.dmdev.spring.integration.http.controller;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mvc;

    @BeforeEach
    void init(){

        //List<GrantedAuthority> roles = Arrays.asList(Role.values());
        //User testUser = new User("test@gmail.com", "test", roles);
        //
        //Authentication authentication = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
        //SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
        //
        //securityContext.setAuthentication(authentication);
        //SecurityContextHolder.setContext(securityContext);

        //Тоже делает аннотация @WithMockUser() !
    }

    @Test
    //@WithMockUser(username = "test@gmail.com",password = "test", authorities = {"ADMIN", "USER"}) //Поставил эту аннотацию над
        // IntegrationTestBase, чтобы все методы были инициализированы для прохождеия аутентификации при тестировании!
    void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                        //.with(SecurityMockMvcRequestPostProcessors.user("test@gmail.com").authorities(Role.ADMIN))
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    @Test
    void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .param(UserCreateEditDto.Fields.username, "test@gmail.com")
                .param(UserCreateEditDto.Fields.firstname, "test")
                .param(UserCreateEditDto.Fields.lastname, "test")
                .param(UserCreateEditDto.Fields.role, "ADMIN")
                .param(UserCreateEditDto.Fields.companyId, "1")
                .param(UserCreateEditDto.Fields.birthDate, "2000-10-11")// нужен конвертер для даты! (Теперь добавлен!)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().is3xxRedirection(),
                        MockMvcResultMatchers.redirectedUrlPattern("/users/{\\d+}")
                );
    }
}