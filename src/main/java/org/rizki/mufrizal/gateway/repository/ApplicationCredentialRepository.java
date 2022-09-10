package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApplicationCredentialRepository extends ReactiveCrudRepository<ApplicationCredential, String> {
}
