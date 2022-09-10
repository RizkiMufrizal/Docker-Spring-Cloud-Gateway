package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.rizki.mufrizal.gateway.repository.ApplicationCredentialRepository;
import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
        applicationCredential.setId(UUID.randomUUID().toString());
        applicationCredential.setApiKey(UUID.randomUUID().toString());
        applicationCredential.setIsNewRecord(true);
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
    public Mono<ApplicationCredential> regerateApiKey(String id) {
        return applicationCredentialRepository.findById(id)
                .map(applicationCredential -> {
                    applicationCredential.setApiKey(UUID.randomUUID().toString());
                    return applicationCredential;
                })
                .flatMap(applicationCredentialRepository::save)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("application credential with id %s not found", id))));
    }

    @Override
    public Mono<Void> deleteApplicationCredential(String id) {
        return applicationCredentialRepository.findById(id)
                .flatMap(applicationCredential -> {
                    if (applicationCredential.getId() != null) {
                        return applicationCredentialRepository.deleteById(applicationCredential.getId());
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<ApplicationCredential> findById(String id) {
        return applicationCredentialRepository.findById(id);
    }

    @Override
    public Mono<Long> count() {
        return applicationCredentialRepository.count();
    }

    @Override
    public Flux<ApplicationCredential> findAllByApiRoute(String apiRoute) {
        return applicationCredentialRepository.findAllByApiRoute(apiRoute);
    }
}