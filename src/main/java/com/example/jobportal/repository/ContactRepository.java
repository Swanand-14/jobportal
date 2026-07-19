package com.example.jobportal.repository;

import com.example.jobportal.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findContactsByStatus(String status);
    List<Contact> findContactsByStatus(String Status, Sort sort);
    Page<Contact> findContactsByStatus(String Status, Pageable pageable);

    List<Contact> findContactsByStatusOrderByCreatedAtAsc(String status);
}