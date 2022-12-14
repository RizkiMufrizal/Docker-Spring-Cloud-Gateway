package org.rizki.mufrizal.gateway.router;

import lombok.AllArgsConstructor;
import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.RouteMetadataUtils;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class ApiPathRouteLocatorImpl implements RouteLocator {
    private final ApiRouteService apiRouteService;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return apiRouteService.findAllByEnableIsTrue()
                .map(apiRoute ->
                        routesBuilder.route(String.valueOf(apiRoute.getId()), predicateSpec ->
                                setPredicateSpec(apiRoute, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute apiRoute, PredicateSpec predicateSpec) {
        BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getPath());
        if (apiRoute.getMethod() != null && !apiRoute.getMethod().isEmpty() && !apiRoute.getMethod().equals("*")) {
            booleanSpec.and().method(apiRoute.getMethod());
        }
        booleanSpec.filters(f -> f.rewritePath(apiRoute.getRewriteFrontend(), apiRoute.getRewriteBackend()));
        booleanSpec.metadata(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, apiRoute.getResponseTimeout());
        booleanSpec.metadata(RouteMetadataUtils.CONNECT_TIMEOUT_ATTR, apiRoute.getConnectTimeout());
        return booleanSpec.uri(apiRoute.getUri());
    }
}