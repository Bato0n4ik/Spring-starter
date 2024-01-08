package com.dmdev.spring.http.rest;

import com.dmdev.spring.dto.PageResponse;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.service.UserService;
import com.dmdev.spring.validation.group.CreateAction;
import com.dmdev.spring.validation.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)//параметр по умолчанию, ставить не обязательно!
    public PageResponse<UserReadDto> findAll(UserFilter userFilter, Pageable pageable){
        var page = userService.findAll(userFilter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id){
        return userService.findById(id).orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //@GetMapping(value = "{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    //public byte[] findAvatar(@PathVariable("id") Long id){
    //    return userService.findAvatar(id)
    //            .orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    //}

    @GetMapping(value = "{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id){
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)// так же это значение задано по умолчанию
    @ResponseStatus(HttpStatus.CREATED)
    public  UserReadDto create(@Validated({Default.class, CreateAction.class}) @RequestBody UserCreateEditDto user){
        return userService.create(user);
    }

    @PutMapping ("{id}")
    public UserReadDto update(@PathVariable("id") Long id, @Validated({Default.class, UpdateAction.class}) @RequestBody UserCreateEditDto user){
        return userService.update(id, user).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id){
        return userService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

    }
}
