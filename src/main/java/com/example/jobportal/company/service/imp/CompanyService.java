package com.example.jobportal.company.service.imp;

import com.example.jobportal.dto.CompanyDto;
import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Company;
import com.example.jobportal.entity.Job;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.company.service.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;
//    public CompanyService(CompanyRepository companyRepository){
//        this.companyRepository = companyRepository;
//    }


    @Override
    public List<CompanyDto> getAllCompanies(){

        List<Company> companylist =  companyRepository.findAllWithJobsByStatus("ACTIVE");
        return companylist.stream().map(this::transformToData).collect(Collectors.toList());



    }
    private CompanyDto transformToData(Company company){
        List<JobDto> jobDtos = company.getJobs().stream()
                .map(this::transformJobToDto)
                .collect(Collectors.toList());
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(),jobDtos);
    }

    private JobDto transformJobToDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getCompany().getLogo(),
                job.getLocation(),
                job.getWorkType(),
                job.getJobType(),
                job.getCategory(),
                job.getExperienceLevel(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getSalaryCurrency(),
                job.getSalaryPeriod(),
                job.getDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getApplicationsCount(),
                job.getFeatured(),
                job.getUrgent(),
                job.getRemote(),
                job.getStatus()
        );
    }
}
