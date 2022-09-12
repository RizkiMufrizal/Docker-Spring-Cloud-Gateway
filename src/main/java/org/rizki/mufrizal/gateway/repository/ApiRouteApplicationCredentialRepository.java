package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApiRouteApplicationCredential;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ApiRouteApplicationCredentialRepository extends ReactiveCrudRepository<ApiRouteApplicationCredential, String> {
    Mono<Void> deleteByApiRouteIdAndApplicationCredentialId(String routeId, String applicationId);
}
