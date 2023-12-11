package com.dcorp.hightech.api.gateway.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * This filter will be executed before the request is routed to destination microservice.
 * To perform that. Classes must implement GlobalFilter interface.
 *
 * @Order:
 * To arrange the order of filter class, we implement Ordered interface and override method in this interface.
 * Then return the number of order that we want.
 */
@Component
public class PreGlobalFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PreGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("My First Pre-filter is executed....");

        String urlPath = exchange.getRequest().getPath().toString();
        logger.info("Request Path: " + urlPath);

        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerNames = headers.keySet();
        headerNames.forEach((headerName) -> {
            String headerValue = headers.getFirst(headerName);
            logger.info(headerName + ": " + headerValue);
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2; //this filter will be invoked at third time
    }
}
