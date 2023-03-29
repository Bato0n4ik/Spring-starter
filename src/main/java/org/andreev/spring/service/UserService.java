package org.andreev.spring.service;
import lombok.RequiredArgsConstructor;
import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.datsbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

}
