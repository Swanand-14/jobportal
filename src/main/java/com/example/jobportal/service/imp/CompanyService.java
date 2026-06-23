package com.example.jobportal.service.imp;

import com.example.jobportal.dto.CompanyDto;
import com.example.jobportal.entity.Company;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.service.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;
//    public CompanyService(CompanyRepository companyRepository){
//        this.companyRepository = companyRepository;
//    }


    @Override
    public List<CompanyDto> getAllCompanies(){

        List<Company> companylist =  companyRepository.findAll();
        return companylist.stream().map(this::transformToData).collect(Collectors.toList());



    }
    private CompanyDto transformToData(Company company){
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt());
    }
}
