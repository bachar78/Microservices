package com.bachar.apigw.security;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class ApiKeyAuthorizationFilter implements GlobalFilter, Ordered {

   //ServerWebExchange provides access to the request and response
    //Gateway filter chain, allow webFilter to delegate to the next in the chain

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        System.out.println("ApiKeyAuthorizationFilter .... checking the key");

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
//        return chain.filter(exchange);
    }

    //This filter will be executed the last in order having the lowest precedence, in order to not interfere with any of by default apigw filters
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
