package com.dcorp.hightech.api.gateway.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * If we do not use the @Order annotation to arrange the query order of the filters,
 * the filters will be queried in order from top to bottom.
 */
@Configuration
public class GlobalFilterConfiguration {
    final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Bean
    @Order(2) // This filter will be invoked at the end.
    public GlobalFilter secondFilter() {
        return ((exchange, chain) -> {
            logger.info("My second global pre-filter is executed.....");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info("My Second post-filter is executed....")));
        });
    }

    @Bean
    @Order(1)
    public GlobalFilter thirdFilter() {
        return ((exchange, chain) -> {
            logger.info("My third global pre-filter is executed.....");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info("My third post-filter is executed....")));
        });
    }

    @Bean
    public GlobalFilter fourthFilter() {
        return ((exchange, chain) -> {
            logger.info("My fourth global pre-filter is executed.....");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info("My fourth post-filter is executed....")));
        });
    }

}
