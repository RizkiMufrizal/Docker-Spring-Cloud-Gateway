package org.rizki.mufrizal.gateway.controller;

import org.rizki.mufrizal.gateway.service.ApplicationCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(value = "/administrator")
public class ApplicationCredentialController {

    @Autowired
    private ApplicationCredentialService applicationCredentialService;

    @GetMapping(value = "/applicationcredential")
    public Mono<Rendering> apiroute() {
        return Mono.just(Rendering.view("applicationcredential")
                .modelAttribute("application_credentials", applicationCredentialService.findApplicationCredential()).build());
    }

}
