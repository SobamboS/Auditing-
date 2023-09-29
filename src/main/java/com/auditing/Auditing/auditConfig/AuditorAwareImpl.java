package com.auditing.Auditing.auditConfig;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Sobambo");

    }

    // User is supposed to be gotten from Spring security

}
