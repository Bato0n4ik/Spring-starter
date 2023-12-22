package com.dmdev.spring.integration.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    @Commit
    void checkAuditing(){
        var ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        userRepository.flush();
    }

    @Test
    void checkCustomImplementation(){
        var filter = new UserFilter(null, "%ov%", LocalDate.now());
        var user = userRepository.findAllByFilter(filter);
    }

    @Test
    void checkProjections(){
        var users = userRepository.findAllByCompanyId(1);
        Assertions.assertThat(users).hasSize(2);
    }

    @Test
    void checkPageable(){
        var page = PageRequest.of(1, 2, Sort.by("id"));
        var slice =  userRepository.findAllBy(page);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));

        while(slice.hasNext()){
            slice =  userRepository.findAllBy((PageRequest) slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getCompany().getName()));
        }

    }

    @Test
    void checkSort(){
        //var sort = Sort.by("firstname").and(Sort.by("lastname"));
        //var sort = Sort.by("id").descending();
        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname)).descending();

        var allUsers = userRepository.findTopBy(LocalDate.now(), sort);
        Assertions.assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkFirstTop(){



        var topUser = userRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

    @Test
    void checkQueries(){
        var users = userRepository.findAllBy("a", "ov");
        Assertions.assertThat(users).hasSize(3);
    }

    @Test
    void checkUpdate(){
        var ivan = userRepository.getById(1L);
        assertSame(Role.ADMIN, ivan.getRole());

        int numbOfChanges = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(numbOfChanges, 2);

        var theSameIvan = userRepository.getById(1L);
        assertSame(Role.USER, theSameIvan.getRole());
    }

}