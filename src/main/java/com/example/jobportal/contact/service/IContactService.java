package com.example.jobportal.contact.service;

import com.example.jobportal.dto.ContactRequestDto;
import com.example.jobportal.dto.ContactResponseDto;

import java.util.List;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
    List<ContactResponseDto> fetchNewContactMsgs();
}
