package com.example.jobportal.contact.service.imp;

import com.example.jobportal.constants.ApplicationConstants;
import com.example.jobportal.contact.service.IContactService;
import com.example.jobportal.dto.ContactRequestDto;
import com.example.jobportal.dto.ContactResponseDto;
import com.example.jobportal.entity.Contact;
import com.example.jobportal.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tools.jackson.databind.util.BeanUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImp implements IContactService {
    private final ContactRepository contactRepository;
    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto){
        boolean result = false;
       Contact contact =  contactRepository.save(transformToEntity(contactRequestDto));
       if(contact!=null && contact.getId()!=null){
           result = true;

       }

       return result;

    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto,contact);
//        contact.setCreatedAt(Instant.now());
//        contact.setCreatedBy("System");
        contact.setStatus("NEW");

        return contact;

    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgs() {
        List<Contact> contacts = contactRepository.findContactByStatus("NEW");
        List<ContactResponseDto> responseDtos = contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
        return responseDtos;

    }
    private ContactResponseDto transformToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto(contact.getId(),
                contact.getName(), contact.getEmail(), contact.getUserType(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(), contact.getCreatedAt());
        return contactResponseDto;
    }


}
