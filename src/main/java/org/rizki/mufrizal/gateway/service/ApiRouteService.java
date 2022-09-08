package org.rizki.mufrizal.gateway.service;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import reactor.core.publisher.Flux;

public interface ApiRouteService {
    Flux<ApiRoute> findApiRoutes();
}
