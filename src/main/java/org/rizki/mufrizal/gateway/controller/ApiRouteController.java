package org.rizki.mufrizal.gateway.controller;

import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(value = "/administrator")
public class ApiRouteController {

    @Autowired
    private ApiRouteService apiRouteService;

    @Autowired
    private ApplicationCredentialService applicationCredentialService;

    @GetMapping(value = "/dashboard")
    public Mono<Rendering> index() {
        return Mono.just(Rendering.view("index")
                .modelAttribute("total_application_credential", applicationCredentialService.count())
                .modelAttribute("total_api_route", apiRouteService.count()).build());
    }

    @GetMapping(value = "/apiroute")
    public Mono<Rendering> apiroute() {
        return Mono.just(Rendering.view("apiroute")
                .modelAttribute("api_routes", apiRouteService.findApiRoutes()).build());
    }

}