package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApplicationCredentialService {
    Flux<ApplicationCredential> findApplicationCredential();

    Mono<ApplicationCredential> createApplicationCredential(ApplicationCredential applicationCredential);

    Mono<ApplicationCredential> updateApplicationCredential(String id, ApplicationCredential applicationCredential);

    Mono<ApplicationCredential> regerateApiKey(String id);

    Mono<Void> deleteApplicationCredential(String id);

    Mono<ApplicationCredential> findById(String id);

    Mono<Long> count();
}
