package com.example.jobportal.jobs.service.imp;

import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.JobPortalUser;
import com.example.jobportal.jobs.service.IjobService;
import com.example.jobportal.repository.JobPortalUserRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.util.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class JobServiceImpl implements IjobService {
    private final JobRepository jobRepository;
    private final JobPortalUserRepository userRepository;
    @Override
    public List<JobDto> getEmployerJobs(String employerEmail) {
        JobPortalUser employer = userRepository.findUserByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer does not have a company assigned");
        }

        List<Job> jobs = employer.getCompany().getJobs();
        return jobs.stream()
                .map(job -> ApplicationUtility.transformJobToDto(job))
                .collect(Collectors.toList());
    }

}
