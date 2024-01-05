package com.dmdev.spring.http.controller;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.service.CompanyService;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model, UserFilter userFilter){
        model.addAttribute("users", userService.findAll(userFilter));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){

        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", userService.findById(id));
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user ){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserCreateEditDto user, RedirectAttributes redirectAttributes){

        //redirectAttributes.addFlashAttribute("user", user);//добавляем в сессию (SessionScope) атрбуты
        // чтобы заполненные поля сохранялись в случии ошибки валидации!
        return "redirect:/users/" + userService.create(user).getId();
    }

    //@PatchMapping("{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto user){
        return userService.update(id, user)
                .map(userReadDto -> "redirect:/users/{id}")
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //@DeleteMapping("{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        if(!userService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}
