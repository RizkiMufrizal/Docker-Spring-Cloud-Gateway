package org.rizki.mufrizal.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.gateway.constant.AuthenticationSchema;
import org.rizki.mufrizal.gateway.domain.ApiRoute;
import org.rizki.mufrizal.gateway.mapper.response.GeneralResponse;
import org.rizki.mufrizal.gateway.service.ApiRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Configuration
@Slf4j
public class AuthenticationApiKeyPreFilter implements GlobalFilter, Ordered {

    @Autowired
    private ApiRouteService apiRouteService;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route != null && route.getId() != null) {
            log.info("route id : {}", route.getId());
            return apiRouteService.findById(route.getId())
                    .map(apiRoute -> this.proceedValidateApiKey(exchange, chain, apiRoute))
                    .flatMap(x -> x);
        } else {
            return this.onError(exchange, "403", "Route Not Found");
        }
    }

    private Mono<Void> proceedValidateApiKey(ServerWebExchange exchange, GatewayFilterChain chain, ApiRoute apiRoute) {
        if (apiRoute.getAuthentication() != null && apiRoute.getAuthentication().equals(AuthenticationSchema.APIKEY)) {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            if (!serverHttpRequest.getHeaders().containsKey(AuthenticationSchema.APIKEY)) {
                log.error("header x-api-key not found");
                return this.onError(exchange, "403", "header x-api-key not found");
            }
            String apiKey = serverHttpRequest.getHeaders().getFirst(AuthenticationSchema.APIKEY);
            if (apiKey != null && apiKey.isEmpty()) {
                log.error("header x-api-key empty");
                return this.onError(exchange, "401", "header x-api-key empty");
            }
            log.info("x-api-key {}", apiKey);
            return apiRouteService.findByApiRouteAndApiKey(apiRoute.getId(), apiKey)
                    .map(x -> {
                        log.info("api route {}", x);
                        return chain.filter(exchange);
                    })
                    .defaultIfEmpty(this.onError(exchange, "401", "Invalid x-api-key"))
                    .flatMap(x -> x);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange serverWebExchange, String code, String message) {
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setCode(code);
        generalResponse.setMessage(message);
        byte[] bytes;
        try {
            bytes = new ObjectMapper().writeValueAsString(generalResponse).getBytes(Charset.defaultCharset());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        serverWebExchange.getResponse().getHeaders().add("Content-Type", "application/json");
        serverWebExchange.getResponse().setStatusCode(HttpStatus.valueOf(Integer.parseInt(code)));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
