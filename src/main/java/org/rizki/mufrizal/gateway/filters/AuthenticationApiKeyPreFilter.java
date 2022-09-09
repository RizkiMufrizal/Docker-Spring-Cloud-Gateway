package org.rizki.mufrizal.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.gateway.constant.AuthenticationSchema;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Component
@Slf4j
public class AuthenticationApiKeyPreFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        if (!serverHttpRequest.getHeaders().containsKey(AuthenticationSchema.APIKEY)) {
            log.info("x-api-key not found");
            return this.onError(exchange);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange serverWebExchange) {
        byte[] bytes = "Invalid API Key".getBytes(Charset.defaultCharset());
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        serverWebExchange.getResponse().getHeaders().add("Content-Type", "application/json");
        serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
