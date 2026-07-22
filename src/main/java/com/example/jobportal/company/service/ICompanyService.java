package com.example.jobportal.company.service;

import com.example.jobportal.dto.CompanyDto;
import com.example.jobportal.entity.Company;

import java.util.List;

public interface ICompanyService {
    List<CompanyDto> getAllCompanies();
    boolean createCompany(CompanyDto companyDto);

    List<CompanyDto> getAllCompaniesForAdmin();
    boolean updateCompanyDetails(Long id, CompanyDto companyDto);

    void deleteCompanyById(Long aLong);
}
