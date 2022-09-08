package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute, Long> {
}
