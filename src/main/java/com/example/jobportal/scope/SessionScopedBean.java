package com.example.jobportal.scope;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Getter @Setter
public class SessionScopedBean {
    private String username;
    public SessionScopedBean(){
        System.out.print("Session Bean created");
    }

}
