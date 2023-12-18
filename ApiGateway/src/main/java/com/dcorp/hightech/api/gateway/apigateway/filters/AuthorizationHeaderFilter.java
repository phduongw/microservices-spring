package com.dcorp.hightech.api.gateway.apigateway.filters;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> implements Ordered {
    Logger logger = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);

    @Autowired
    Environment environment;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("Auth jwt token");
            if (request.getHeaders().get(HttpHeaders.AUTHORIZATION) != null ) {
                String authorizationHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                String token = authorizationHeader.replace("Bearer", "");
                if (!isJwtValid(token)) {
                    return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
                }

                return chain.filter(exchange);
            }

            return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        String tokenSecret = environment.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        String userID;

        JwtParser jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build();
        try {
            Jwt<Header, Claims> parsedToken = jwtParser.parse(jwt);
            userID = parsedToken.getBody().getSubject();
            System.out.println("User ID: " + userID);
        } catch (Exception ex) {
            return false;
        }

        return !Objects.isNull(userID) && !userID.isEmpty();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static class Config {
        // Todo: Put configuration properties here
    }

}
