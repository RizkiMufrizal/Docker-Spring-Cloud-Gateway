package org.rizki.mufrizal.gateway.router;

import lombok.AllArgsConstructor;
import org.rizki.mufrizal.gateway.constant.AuthenticationSchema;
import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.filters.AuthenticationApiKeyPreFilter;
import org.rizki.mufrizal.gateway.filters.PreGlobalFilter;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class ApiPathRouteLocatorImpl implements RouteLocator {
    private final ApiRouteService apiRouteService;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return apiRouteService.findApiRoutes()
                .map(apiRoute -> routesBuilder.route(String.valueOf(apiRoute.getId()),
                        predicateSpec -> setPredicateSpec(apiRoute, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute apiRoute, PredicateSpec predicateSpec) {
        BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getPath());
        if (apiRoute.getMethod() != null && !apiRoute.getMethod().isEmpty()) {
            booleanSpec.and().method(apiRoute.getMethod());
        }
        if (apiRoute.getAuthentication() != null && !apiRoute.getAuthentication().isEmpty() && apiRoute.getAuthentication().equals(AuthenticationSchema.APIKEY)) {
            booleanSpec.filters(f -> f.filter(new AuthenticationApiKeyPreFilter()));
        }
        booleanSpec.filters(f -> f.rewritePath(apiRoute.getRewriteFrontend(), apiRoute.getRewriteBackend()));
        return booleanSpec.uri(apiRoute.getUri());
    }
}