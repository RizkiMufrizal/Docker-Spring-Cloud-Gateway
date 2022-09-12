package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApiRouteApplicationCredential;
import reactor.core.publisher.Mono;

public interface ApiRouteApplicationCredentialService {
    Mono<ApiRouteApplicationCredential> grantAccess(String routeId, String applicationId);

    Mono<Void> revokeAccess(String routeId, String applicationId);
}
