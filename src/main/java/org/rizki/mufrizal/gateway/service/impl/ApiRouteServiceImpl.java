package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.repository.ApiRouteRepository;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.rizki.mufrizal.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiRouteServiceImpl implements ApiRouteService {

    @Autowired
    private ApiRouteRepository apiRouteRepository;

    @Autowired
    private GatewayRouteService gatewayRouteService;

    @Override
    public Flux<ApiRoute> findApiRoutes() {
        return apiRouteRepository.findAll();
    }

    @Override
    public Mono<ApiRoute> createApiRoute(ApiRoute apiRoute) {
        return apiRouteRepository.save(apiRoute).doOnSuccess(x -> gatewayRouteService.refreshRoutes());
    }

    @Override
    public Mono<ApiRoute> updateApiRoute(String id, ApiRoute apiRoute) {
        return apiRouteRepository.findById(id)
                .map(x -> apiRoute)
                .flatMap(apiRouteRepository::save)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Api route with id %s not found", id))))
                .doOnSuccess(x -> gatewayRouteService.refreshRoutes());
    }

    @Override
    public Mono<Void> deleteApiRoute(String id) {
        return apiRouteRepository.findById(id)
                .flatMap(apiRoute -> apiRouteRepository.deleteById(apiRoute.getId()))
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Api route with id %s not found", id))))
                .doOnSuccess(x -> gatewayRouteService.refreshRoutes());
    }

    @Override
    public Mono<Long> count() {
        return apiRouteRepository.count();
    }

    @Override
    public Mono<ApiRoute> findById(String id) {
        return apiRouteRepository.findById(id);
    }

    @Override
    public Mono<ApiRoute> findByApiRouteAndApiKey(String apiRoute, String apiKey) {
        return apiRouteRepository.findByApiRouteAndApiKey(apiRoute, apiKey);
    }
}