package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.rizki.mufrizal.gateway.repository.ApplicationCredentialRepository;
import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApplicationCredentialServiceImpl implements ApplicationCredentialService {

    @Autowired
    private ApplicationCredentialRepository applicationCredentialRepository;

    @Override
    public Flux<ApplicationCredential> findApplicationCredential() {
        return applicationCredentialRepository.findAll();
    }

    @Override
    public Mono<ApplicationCredential> createApplicationCredential(ApplicationCredential applicationCredential) {
        return applicationCredentialRepository.save(applicationCredential);
    }

    @Override
    public Mono<ApplicationCredential> updateApplicationCredential(String id, ApplicationCredential applicationCredential) {
        return applicationCredentialRepository.findById(id)
                .map(x -> applicationCredential)
                .flatMap(applicationCredentialRepository::save)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("application credential with id %s not found", id))));
    }

    @Override
    public Mono<Void> deleteApplicationCredential(String id) {
        return applicationCredentialRepository.findById(id)
                .flatMap(applicationCredential -> applicationCredentialRepository.deleteById(applicationCredential.getId()))
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("application credential with id %s not found", id))));
    }

    @Override
    public Mono<Long> count() {
        return applicationCredentialRepository.count();
    }
}