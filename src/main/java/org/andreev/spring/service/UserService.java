package org.andreev.spring.service;
import org.andreev.spring.datsbase.entity.Company;
import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.repository.CrudRepository;
import org.andreev.spring.datsbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;


    public UserService(UserRepository userRepository, CrudRepository<Integer, Company> companyRepository1) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository1;
    }
}
