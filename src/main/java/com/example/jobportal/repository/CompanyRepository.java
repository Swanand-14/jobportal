package com.example.jobportal.repository;

import com.example.jobportal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status = :status")
   List<Company>findAllWithJobsByStatus(@Param("status") String status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""

UPDATE Company c

SET

c.name=:name,

c.logo=:logo,

c.industry=:industry,

c.size=:size,

c.rating=:rating,

c.locations=:locations,

c.founded=:founded,

c.description=:description,

c.employees=:employees,

c.website=:website

WHERE c.id=:id

""")
    int updateCompanyDetails(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("logo") String logo,
            @Param("industry") String industry,
            @Param("size") String size,
            @Param("rating") BigDecimal rating,
            @Param("locations") String locations,
            @Param("founded") Integer founded,
            @Param("description") String description,
            @Param("employees") Integer employees,
            @Param("website") String website
    );
}
