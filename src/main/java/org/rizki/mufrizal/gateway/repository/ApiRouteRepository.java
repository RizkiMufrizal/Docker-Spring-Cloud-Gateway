package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute, String> {
    @Query("SELECT ap.id, ap.path, ap.uri, ap.authentication, ap.method, ap.rewrite_frontend, ap.rewrite_backend, ac.application_name, ac.api_key " +
            "FROM tb_api_route ap " +
            "INNER JOIN tb_api_route_application_credential apc ON ap.id = apc.api_route_id " +
            "INNER JOIN tb_application_credential ac ON apc.application_credential_id = ac.id " +
            "WHERE ap.id = :apiRoute and ac.api_key = :apiKey")
    Mono<ApiRoute> findByApiRouteAndApiKey(String apiRoute, String apiKey);

    @Query("SELECT ap.id, ap.name, ap.version, ap.path, ap.uri, ap.authentication, ap.authentication, ap.method, ap.rewrite_frontend, ap.rewrite_backend " +
            "FROM tb_api_route ap " +
            "INNER JOIN tb_api_route_application_credential apc ON ap.id = apc.api_route_id " +
            "INNER JOIN tb_application_credential ac ON apc.application_credential_id = ac.id " +
            "WHERE ac.id = :id")
    Flux<ApiRoute> findByApplicationCredential(String id);

    @Query("SELECT ap.id, ap.name, ap.version, ap.path, ap.uri, ap.authentication, ap.authentication, ap.method, ap.rewrite_frontend, ap.rewrite_backend " +
            "FROM tb_api_route ap " +
            "WHERE ap.authentication NOT in ('passthrough') AND ap.id NOT IN (SELECT apc.api_route_id FROM tb_application_credential ac INNER JOIN tb_api_route_application_credential apc ON ac.id = apc.application_credential_id WHERE ac.id = :id)")
    Flux<ApiRoute> findByNotApplicationCredential(String id);

    Flux<ApiRoute> findAllByEnableIsTrue();

    Mono<Long> countAllByEnableIsTrue();

    Mono<ApiRoute> findByIdAndEnableIsTrue(String id);
}
