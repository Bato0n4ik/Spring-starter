package org.andreev.spring.dto;

import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.springframework.stereotype.Component;

public record CompanyReadDto(Integer id) {

}
