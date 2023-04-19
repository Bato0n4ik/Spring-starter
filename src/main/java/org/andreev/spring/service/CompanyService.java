package org.andreev.spring.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {


    private final CrudRepository<Integer, Company> companyRepository;

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id).
                map(company -> {
                    eventPublisher.publishEvent(new EntityEvent(company, AccessType.READ));
                    return new CompanyReadDto(company.getId());
                });
    }
}
