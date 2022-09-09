package org.rizki.mufrizal.gateway.filters;

import lombok.extern.slf4j.Slf4j;
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

    public static final String ORIGINAL_REQUEST_BODY = "originalRequestBody";

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
                                exchange.getAttributes().put(ORIGINAL_REQUEST_BODY, body);
                                return Mono.just(body);
                            }
                            return Mono.empty();
                        }))
                .filter(exchange, chain)
                .then(Mono.fromRunnable(() -> this.writeLog(exchange)));
    }

    private void writeLog(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String requestBody = exchange.getAttribute(PreGlobalFilter.ORIGINAL_REQUEST_BODY);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        sb.append("URI: ").append(uri).append("\n");
        sb.append("Method: ").append(request.getMethod()).append("\n");
        sb.append("Request Headers: ");

        request.getHeaders().forEach((key, value) -> sb.append(key).append("=").append(value).append(", "));
        sb.append("\n");
        sb.append("Request Body: ").append(requestBody).append("\n");

        log.info(sb.toString());
        exchange.getAttributes().remove(PreGlobalFilter.ORIGINAL_REQUEST_BODY);
    }

}