package org.rizki.mufrizal.gateway.controller;

import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        Mono<Long> countEnable = apiRouteService.countAllByEnableIsTrue();
        Mono<Long> countAll = apiRouteService.count();
        return Mono.just(Rendering.view("index")
                .modelAttribute("total_application_credential", applicationCredentialService.count())
                .modelAttribute("total_api_route_active", countEnable)
                .modelAttribute("total_api_route_inactive", Mono.zip(countAll, countEnable).map(x -> x.getT1() - x.getT2()))
                .modelAttribute("total_api_route", countAll).build());
    }

    @GetMapping(value = "/apiroute")
    public Mono<Rendering> apiroute() {
        return Mono.just(Rendering.view("apiroute")
                .modelAttribute("api_routes", apiRouteService.findApiRoutes()).build());
    }

    @GetMapping(value = "/apiroute/new")
    public Mono<Rendering> apirouteNew() {
        return Mono.just(Rendering.view("apiroutenew")
                .modelAttribute("api_route", new ApiRoute()).build());
    }

    @PostMapping(value = "/apiroute/save")
    public Mono<?> apirouteSave(@ModelAttribute ApiRoute apiRoute) {
        return apiRouteService.createApiRoute(apiRoute)
                .thenReturn("redirect:/administrator/apiroute");
    }

    @GetMapping(value = "/apiroute/edit/{id}")
    public Mono<Rendering> apirouteEdit(@PathVariable("id") String id) {
        return Mono.just(Rendering.view("apirouteedit")
                .modelAttribute("id", id)
                .modelAttribute("api_route", apiRouteService.findById(id)).build());
    }

    @PostMapping(value = "/apiroute/update/{id}")
    public Mono<?> apirouteUpdate(@PathVariable("id") String id, @ModelAttribute ApiRoute apiRoute) {
        return apiRouteService.updateApiRoute(id, apiRoute)
                .thenReturn("redirect:/administrator/apiroute");
    }

    @GetMapping(value = "/apiroute/delete/{id}")
    public Mono<String> apirouteDelete(@PathVariable("id") String id) {
        return apiRouteService.deleteApiRoute(id)
                .thenReturn("redirect:/administrator/apiroute");
    }

    @GetMapping(value = "/apiroute/applicationcredentials/{id}")
    public Mono<Rendering> apirouteDetailApplicationCredentials(@PathVariable("id") String id) {
        return Mono.just(Rendering.view("apirouteapplicationcredentials")
                .modelAttribute("api_route", apiRouteService.findById(id))
                .modelAttribute("application_credentials", applicationCredentialService.findAllByApiRoute(id)).build());
    }

    @GetMapping(value = "/apiroute/status/{id}")
    public Mono<String> apirouteStatus(@PathVariable("id") String id) {
        return apiRouteService.statusApiRoute(id)
                .thenReturn("redirect:/administrator/apiroute");
    }

}