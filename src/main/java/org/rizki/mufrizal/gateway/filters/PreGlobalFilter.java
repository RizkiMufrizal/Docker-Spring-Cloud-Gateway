package org.rizki.mufrizal.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.gateway.constant.GatewayConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@Slf4j
public class PreGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private ModifyRequestBodyGatewayFilterFactory filterFactory;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return filterFactory
                .apply(new ModifyRequestBodyGatewayFilterFactory.Config()
                        .setRewriteFunction(String.class, String.class, (newExchange, body) -> {
                            if (body != null) {
                                exchange.getAttributes().put(GatewayConstant.ORIGINAL_REQUEST_BODY, body);
                                return Mono.just(body);
                            }
                            return Mono.empty();
                        }))
                .filter(exchange, chain)
                .then(Mono.fromRunnable(() -> this.writeLog(exchange)));
    }

    private void writeLog(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String requestBody = exchange.getAttribute(GatewayConstant.ORIGINAL_REQUEST_BODY);
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

        log.info("Request URI: {}", uri);
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Body: {}", requestBody);
        try {
            log.info("Request Headers: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request.getHeaders()));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}