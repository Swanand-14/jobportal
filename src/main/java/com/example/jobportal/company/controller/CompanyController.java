package com.example.jobportal.company.controller;

import com.example.jobportal.dto.CompanyDto;
import com.example.jobportal.company.service.ICompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor

public class CompanyController {
    private final ICompanyService companyService;
//    public CompanyController(ICompanyService companyService){
//        this.companyService = companyService;
//    }

    @GetMapping(path = "/public" , version = "1.0")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(){
        log.info("getting the company list");
        List<CompanyDto> companyList = companyService.getAllCompanies();
        return ResponseEntity.ok().body(companyList);
    }

}
