package com.dmdev.spring.service;

import com.dmdev.spring.database.entity.QUser;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.querydsl.QPredicates;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.mapper.UserCreateEditMapper;
import com.dmdev.spring.mapper.UserReadMapper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    private final ImageService imageService;

    public Page<UserReadDto> findAll(UserFilter userFilter, Pageable pageable){

        Predicate predicate = QPredicates.builder()
                .add(userFilter.firstname(), QUser.user.firstname::containsIgnoreCase)
                .add(userFilter.lastname(), QUser.user.lastname::containsIgnoreCase)
                .add(userFilter.birthDate(), QUser.user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable).map(userReadMapper::map);
    }

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto){
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()){
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id){
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto){
        return userRepository.findById(id)
                .map(user -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);

    }

    @Transactional
    public boolean delete(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            userRepository.flush();
            return true;
        }
        return false;
    }

}
