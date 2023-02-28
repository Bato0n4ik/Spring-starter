package org.andreev.spring.service;
import org.andreev.spring.datsbase.repository.CompanyRepository;
import org.andreev.spring.datsbase.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private CompanyService companyService;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository, CompanyRepository companyRepository1) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository1;
    }

    public void setCompanyService(CompanyService companyService){
        this.companyService = companyService;
    }
}
