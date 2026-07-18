package com.example.jobportal.contact.controller;

import com.example.jobportal.contact.service.IContactService;
import com.example.jobportal.dto.ContactRequestDto;
import com.example.jobportal.dto.ContactResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final IContactService contactService;
    @PostMapping(path = "/public",version = "1.0")
    public ResponseEntity<String> saveContactMsg(@RequestBody @Valid ContactRequestDto contactRequestDto){

            boolean isSaved = contactService.saveContact(contactRequestDto);
            if(isSaved){
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Request processed successfully");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Request processed failed");
            }

        



    }
    @GetMapping
    public ResponseEntity<String> returnStatus(@RequestParam @Validated @NotBlank(message = "Status can not be blank ") String status){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Request Accepted");
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ContactResponseDto>> fetchNewContactMsgs() {
        List<ContactResponseDto> contactResponseDtos = contactService.fetchNewContactMsgs();
        return ResponseEntity.status(HttpStatus.OK).body(contactResponseDtos);
    }


}
