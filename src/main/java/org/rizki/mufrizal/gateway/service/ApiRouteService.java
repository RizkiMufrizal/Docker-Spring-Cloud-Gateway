package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {
    Flux<ApiRoute> findApiRoutes();

    Mono<ApiRoute> findById(String id);

    Mono<ApiRoute> findByApiRouteAndApiKey(String apiRoute, String apiKey);
}
