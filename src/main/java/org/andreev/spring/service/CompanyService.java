package org.andreev.spring.service;

public class CompanyService {

    private UserService userService;


    public CompanyService(UserService userService) {
        this.userService = userService;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
