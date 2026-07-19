package com.example.jobportal.audit;

import com.example.jobportal.util.ApplicationUtility;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("auditorAwareImp")
public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(ApplicationUtility.getLoggedInUser());
    }
}
