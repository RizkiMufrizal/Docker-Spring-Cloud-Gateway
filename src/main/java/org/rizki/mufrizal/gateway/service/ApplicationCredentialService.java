package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import reactor.core.publisher.Mono;

public interface ApplicationCredentialService {
    Mono<ApplicationCredential> findOne(String apiKey);
}
