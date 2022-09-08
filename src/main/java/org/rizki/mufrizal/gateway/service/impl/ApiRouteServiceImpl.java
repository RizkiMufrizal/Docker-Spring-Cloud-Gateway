package org.rizki.mufrizal.gateway.service.impl;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.repository.ApiRouteRepository;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ApiRouteServiceImpl implements ApiRouteService {

    @Autowired
    private ApiRouteRepository apiRouteRepository;

    @Override
    public Flux<ApiRoute> findApiRoutes() {
        return apiRouteRepository.findAll();
    }
}