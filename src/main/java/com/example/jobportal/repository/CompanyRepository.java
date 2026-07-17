package com.example.jobportal.repository;

import com.example.jobportal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status = :status")
   List<Company>findAllWithJobsByStatus(@Param("status") String status);
}
