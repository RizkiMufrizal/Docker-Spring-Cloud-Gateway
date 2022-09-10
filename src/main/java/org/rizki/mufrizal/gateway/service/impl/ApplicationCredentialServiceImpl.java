package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.rizki.mufrizal.gateway.repository.ApplicationCredentialRepository;
import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ApplicationCredentialServiceImpl implements ApplicationCredentialService {

    @Autowired
    private ApplicationCredentialRepository applicationCredentialRepository;

    @Override
    public Mono<ApplicationCredential> findOne(String apiKey) {
        return applicationCredentialRepository.findByApiKey(apiKey);
    }
}