package org.rizki.mufrizal.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.gateway.constant.GatewayConstant;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return filterFactory
                .apply(new ModifyResponseBodyGatewayFilterFactory.Config()
                        .setRewriteFunction(byte[].class, byte[].class, (newExchange, body) -> {
                            exchange.getAttributes().put(GatewayConstant.ORIGINAL_RESPONSE_BODY, new String(body));
                            return Mono.just(body);
                        }))
                .filter(exchange, chain)
                .then(Mono.fromRunnable(() -> this.writeLog(exchange)));
    }

    private void writeLog(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String responseBody = exchange.getAttribute(GatewayConstant.ORIGINAL_RESPONSE_BODY);

        log.info("Response Body: {}", responseBody);
        try {
            log.info("Response Headers: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getHeaders()));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Response Status: {}", response.getRawStatusCode());
    }

}