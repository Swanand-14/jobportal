package com.example.jobportal.contact.service;

import com.example.jobportal.dto.ContactRequestDto;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
}
