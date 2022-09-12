package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApiRouteApplicationCredential;
import org.rizki.mufrizal.gateway.redisreactive.annotation.RedisReactiveCacheEvictAll;
import org.rizki.mufrizal.gateway.repository.ApiRouteApplicationCredentialRepository;
import org.rizki.mufrizal.gateway.service.ApiRouteApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ApiRouteApplicationCredentialServiceImpl implements ApiRouteApplicationCredentialService {

    @Autowired
    private ApiRouteApplicationCredentialRepository applicationCredentialRepository;

    @RedisReactiveCacheEvictAll
    @Override
    public Mono<ApiRouteApplicationCredential> grantAccess(String routeId, String applicationId) {
        ApiRouteApplicationCredential apiRouteApplicationCredential = new ApiRouteApplicationCredential();
        apiRouteApplicationCredential.setIsNewRecord(true);
        apiRouteApplicationCredential.setId(UUID.randomUUID().toString());
        apiRouteApplicationCredential.setApiRouteId(routeId);
        apiRouteApplicationCredential.setApplicationCredentialId(applicationId);
        return applicationCredentialRepository.save(apiRouteApplicationCredential);
    }

    @RedisReactiveCacheEvictAll
    @Override
    public Mono<Void> revokeAccess(String routeId, String applicationId) {
        return applicationCredentialRepository.deleteByApiRouteIdAndApplicationCredentialId(routeId, applicationId);
    }
}
