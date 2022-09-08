package org.rizki.mufrizal.gateway.configuration;

import org.rizki.mufrizal.gateway.router.ApiPathRouteLocatorImpl;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService, RouteLocatorBuilder routeLocatorBuilder) {
        return new ApiPathRouteLocatorImpl(apiRouteService, routeLocatorBuilder);
    }
}