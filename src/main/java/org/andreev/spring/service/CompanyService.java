package org.andreev.spring.service;

import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.dto.CompanyReadDto;
import org.andreev.spring.entity.listener.AccessType;
import org.andreev.spring.entity.listener.EntityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {


    private final CrudRepository<Integer, Company> companyRepository;

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public CompanyService(CrudRepository<Integer, Company> companyRepository, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id).map(company -> {
            eventPublisher.publishEvent(new EntityEvent(company, AccessType.READ));
            return new CompanyReadDto(company.id());
        });
    }
}
