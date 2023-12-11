package com.dcorp.hightech.api.gateway.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * This filter will be executed after the request returns result from destination microservice.
 * To perform that. Classes must implement GlobalFilter interface and after executing the filter, we should call them() method.
 */
@Component
public class PostGlobalFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(PostGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Global Post-filter is executed....");

        }));
    }

}
