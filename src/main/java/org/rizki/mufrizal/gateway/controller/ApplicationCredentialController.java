package org.rizki.mufrizal.gateway.controller;

import org.rizki.mufrizal.gateway.domain.ApplicationCredential;
import org.rizki.mufrizal.gateway.service.ApiRouteApplicationCredentialService;
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
public class ApplicationCredentialController {

    @Autowired
    private ApplicationCredentialService applicationCredentialService;

    @Autowired
    private ApiRouteService apiRouteService;

    @Autowired
    private ApiRouteApplicationCredentialService apiRouteApplicationCredentialService;

    @GetMapping(value = "/applicationcredential")
    public Mono<Rendering> applicationcredential() {
        return Mono.just(Rendering.view("applicationcredential")
                .modelAttribute("application_credentials", applicationCredentialService.findApplicationCredential()).build());
    }

    @GetMapping(value = "/applicationcredential/new")
    public Mono<Rendering> applicationcredentialNew() {
        return Mono.just(Rendering.view("applicationcredentialnew")
                .modelAttribute("application_credential", new ApplicationCredential()).build());
    }

    @PostMapping(value = "/applicationcredential/save")
    public Mono<?> applicationcredentialSave(@ModelAttribute ApplicationCredential applicationCredential) {
        return applicationCredentialService.createApplicationCredential(applicationCredential)
                .thenReturn("redirect:/administrator/applicationcredential");
    }

    @GetMapping(value = "/applicationcredential/edit/{id}")
    public Mono<Rendering> applicationcredentialEdit(@PathVariable("id") String id) {
        return Mono.just(Rendering.view("applicationcredentialedit")
                .modelAttribute("id", id)
                .modelAttribute("application_credential", applicationCredentialService.findById(id)).build());
    }

    @PostMapping(value = "/applicationcredential/update/{id}")
    public Mono<?> applicationcredentialUpdate(@PathVariable("id") String id, @ModelAttribute ApplicationCredential applicationCredential) {
        return applicationCredentialService.updateApplicationCredential(id, applicationCredential)
                .thenReturn("redirect:/administrator/applicationcredential");
    }

    @GetMapping(value = "/applicationcredential/delete/{id}")
    public Mono<String> applicationcredentialDelete(@PathVariable("id") String id) {
        return applicationCredentialService.deleteApplicationCredential(id)
                .thenReturn("redirect:/administrator/applicationcredential");
    }

    @GetMapping(value = "/applicationcredential/regerateapikey/{id}")
    public Mono<String> regerateApiKey(@PathVariable("id") String id) {
        return applicationCredentialService.regerateApiKey(id)
                .thenReturn("redirect:/administrator/applicationcredential");
    }

    @GetMapping(value = "/applicationcredential/apiroute/{id}")
    public Mono<Rendering> applicationCredentialDetailApiroutes(@PathVariable("id") String id) {
        return Mono.just(Rendering.view("applicationcredentialapiroutes")
                .modelAttribute("id", id)
                .modelAttribute("application_credential", applicationCredentialService.findById(id))
                .modelAttribute("not_api_routes", apiRouteService.findByNotApplicationCredential(id))
                .modelAttribute("api_routes", apiRouteService.findByApplicationCredential(id)).build());
    }

    @GetMapping(value = "/applicationcredential/grantaccess/{routeId}/{applicationId}")
    public Mono<String> grantAccess(@PathVariable("routeId") String routeId, @PathVariable("applicationId") String applicationId) {
        return apiRouteApplicationCredentialService.grantAccess(routeId, applicationId)
                .thenReturn("redirect:/administrator/applicationcredential/apiroute/" + applicationId);
    }

    @GetMapping(value = "/applicationcredential/revokeaccess/{routeId}/{applicationId}")
    public Mono<String> revokeAccess(@PathVariable("routeId") String routeId, @PathVariable("applicationId") String applicationId) {
        return apiRouteApplicationCredentialService.revokeAccess(routeId, applicationId)
                .thenReturn("redirect:/administrator/applicationcredential/apiroute/" + applicationId);
    }
}
