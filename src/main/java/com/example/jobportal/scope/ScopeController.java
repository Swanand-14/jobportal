package com.example.jobportal.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scope")
@RequiredArgsConstructor
public class ScopeController {
    private final RequestScopedBean requestScopedBean;
    private final SessionScopedBean sessionScopedBean;
    private final ApplicationScopedBean applicationScopedBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope(){
        requestScopedBean.setUsername("swanand");
        return ResponseEntity.ok().body(requestScopedBean.getUsername());
    }
    @GetMapping("/session")
    public ResponseEntity<String> testSessionScope(){
        sessionScopedBean.setUsername("swanand");
        return ResponseEntity.ok().body(sessionScopedBean.getUsername());
    }
    @GetMapping("/test")
    public ResponseEntity<String> testScope(){
        return ResponseEntity.ok().body(sessionScopedBean.getUsername());
    }

    @GetMapping("/application")
    public ResponseEntity<Integer> ApplicationScope(){
        applicationScopedBean.incrementVisitorsCount();
        return ResponseEntity.ok().body(applicationScopedBean.getVisitorcount());
    }
    @GetMapping("/testapplication")
    public ResponseEntity<Integer> testApplicationScope(){
        
        return ResponseEntity.ok().body(applicationScopedBean.getVisitorcount());
    }



}
