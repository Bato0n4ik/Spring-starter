package com.dmdev.spring.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface PersonalInfo2 {

    String getFirstname();
    String getLastname();
    LocalDate getBirthDate();
    @Value("#{target.getFirstname() + ' ' + target.getLastname()}")
    String getFullName();
}
