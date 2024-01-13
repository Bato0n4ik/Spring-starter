package com.dmdev.spring.config;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Method;
import java.util.Set;

@Configuration
@EnableMethodSecurity// для работы аннтаций @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()//доступ всем пользователям
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.getAuthority())//доступ к страничке только админам
                        .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated()

                )
                //.httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/users")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())));
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(){
        return userRequest -> {

            String email = userRequest.getIdToken().getEmail();
            // TODO: create user userService.create
            var userDetails = userService.loadUserByUsername(email);

            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            Set<Method> methodDetailsMethods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{OidcUserRequest.class, OidcUser.class}
                    , (proxy, method, args) -> methodDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args) : method.invoke(oidcUser, args));
        };
    }

}
