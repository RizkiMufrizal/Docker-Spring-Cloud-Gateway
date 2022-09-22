package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {
    Flux<ApiRoute> findApiRoutes();

    Flux<ApiRoute> findAllByEnableIsTrue();

    Mono<ApiRoute> createApiRoute(ApiRoute apiRoute);

    Mono<ApiRoute> updateApiRoute(String id, ApiRoute apiRoute);

    Mono<Void> deleteApiRoute(String id);

    Mono<Long> count();

    Mono<Long> countAllByEnableIsTrue();

    Mono<ApiRoute> findById(String id);

    Mono<ApiRoute> findByIdAndEnableIsTrue(String id);

    Mono<ApiRoute> findByApiRouteAndApiKey(String apiRoute, String apiKey);

    Flux<ApiRoute> findByApplicationCredential(String id);

    Flux<ApiRoute> findByNotApplicationCredential(String id);

    Mono<ApiRoute> statusApiRoute(String id);
}
