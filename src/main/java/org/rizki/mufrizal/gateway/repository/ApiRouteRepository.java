package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute, String> {
    @Query("SELECT ap.path, ap.uri, ap.authentication, ap.method, ap.rewrite_frontend, ap.rewrite_backend, ac.application_name, ac.api_key " +
            "FROM tb_api_route ap " +
            "INNER JOIN tb_api_route_application_credential apc ON ap.id = apc.api_route_id " +
            "INNER JOIN tb_application_credential ac ON apc.application_credential_id = ac.id " +
            "WHERE ap.id = :apiRoute and ac.api_key = :apiKey")
    Mono<ApiRoute> findByApiRouteAndApiKey(String apiRoute, String apiKey);
}
