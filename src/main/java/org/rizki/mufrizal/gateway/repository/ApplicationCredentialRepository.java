package org.rizki.mufrizal.gateway.repository;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ApplicationCredentialRepository extends ReactiveCrudRepository<ApplicationCredential, String> {
    @Query("SELECT ac.application_name, ac.api_key " +
            "FROM tb_application_credential ac " +
            "INNER JOIN tb_api_route_application_credential apc ON ac.id = apc.application_credential_id " +
            "INNER JOIN tb_api_route ap ON apc.api_route_id = ap.id " +
            "WHERE ap.id = :apiRoute")
    Flux<ApplicationCredential> findAllByApiRoute(String apiRoute);
}
