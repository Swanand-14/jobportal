package com.example.jobportal.contact.service.imp;

import com.example.jobportal.constants.ApplicationConstants;
import com.example.jobportal.contact.service.IContactService;
import com.example.jobportal.dto.ContactRequestDto;
import com.example.jobportal.dto.ContactResponseDto;
import com.example.jobportal.entity.Contact;
import com.example.jobportal.repository.ContactRepository;
import com.example.jobportal.util.ApplicationUtility;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.util.BeanUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImp implements IContactService {
    private final ContactRepository contactRepository;
    @Override
    @Transactional
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
        List<Contact> contacts = contactRepository.findContactsByStatus("NEW");
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

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir) {
        // Create Sort object based on sortBy and sortDir parameters
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus("NEW", sort);
        List<ContactResponseDto> responseDtos = contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
        return responseDtos;
    }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        // Create Pageable object with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        // Fetch paginated and sorted contacts from repository
        Page<Contact> contactPage = contactRepository.findContactsByStatus("NEW", pageable);

        // Transform Contact entities to ContactResponseDto
        Page<ContactResponseDto> responseDtoPage = contactPage.map(this::transformToDto);
        return responseDtoPage;
    }
    @Transactional
    @Override
    public boolean closeContactMsg(Long id, String status) {
        int result = contactRepository.updateStatusById(status,id, ApplicationUtility.getLoggedInUser());
        if(result == 0)return false;

        return true;

    }
}
