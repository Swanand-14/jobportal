package com.example.jobportal.repository;

import com.example.jobportal.entity.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface JobPortalUserRepository extends JpaRepository<JobPortalUser, Long> {
    Optional<JobPortalUser> findUserByEmailAndMobileNumberAllIgnoreCase(String email, String mobileNumber);

    Optional<JobPortalUser> findUserByEmail(String username);


}