package com.dmdev.spring.http.controller;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> roles(){
        return List.of(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model, @ModelAttribute UserReadDto userReadDto){

        model.addAttribute("user", new UserReadDto(1L, "Ivan"));
        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user){
        //ModelAndView modelAndView = new ModelAndView();
        //modelAndView.setViewName("greeting/bye");
        return "greeting/bye";
    }





    @GetMapping("/hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                              @RequestParam("age") Integer age,
                              @RequestHeader String accept,
                              @CookieValue("JSESSIONID") String jsessionId,
                              @PathVariable Integer id){

        var ageParamValue = request.getParameter("age");
        var acceptHeader = request.getHeader("accept");
        var cookies = request.getCookies();


        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }
}
