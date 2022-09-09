package org.rizki.mufrizal.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class PostGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private ModifyResponseBodyGatewayFilterFactory filterFactory;

    public static final String ORIGINAL_RESPONSE_BODY = "originalResponseBody";

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayFilter delegate = filterFactory
                .apply(new ModifyResponseBodyGatewayFilterFactory.Config()
                        .setRewriteFunction(byte[].class, byte[].class, (newExchange, body) -> {
                            if (body != null) {
                                exchange.getAttributes().put(ORIGINAL_RESPONSE_BODY, new String(body));
                                return Mono.just(body);
                            }
                            return Mono.empty();
                        }));
        return delegate
                .filter(exchange, chain)
                .then(Mono.fromRunnable(() -> {
                    this.writeLog(exchange);
                }));
    }

    private void writeLog(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        String responseBody = exchange.getAttribute(PostGlobalFilter.ORIGINAL_RESPONSE_BODY);

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        sb.append("\n");
        sb.append("Response Status: ").append(response.getRawStatusCode()).append("\n");
        sb.append("Response Headers: ");
        response.getHeaders().forEach((key, value) -> sb.append(key).append("=").append(value).append(", "));
        sb.append("\n");
        sb.append("Response Body: ").append(responseBody).append("\n");

        log.info(sb.toString());
        exchange.getAttributes().remove(PostGlobalFilter.ORIGINAL_RESPONSE_BODY);
    }

}