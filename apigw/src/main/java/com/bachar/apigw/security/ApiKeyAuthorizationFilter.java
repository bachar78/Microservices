package com.bachar.apigw.security;


import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@AllArgsConstructor
public class ApiKeyAuthorizationFilter implements GlobalFilter, Ordered {

    private final FakeApiAuthorizationChecker checker;



   //ServerWebExchange provides access to the request and response and attributes 
    //Gateway filter chain, allow webFilter to delegate to the next in the chain

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        System.out.println("ApiKeyAuthorizationFilter .... checking the key");

        Route attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String application = attribute.getId();
        List<String> apiKey = exchange.getRequest().getHeaders().get("ApiKey");
        if(application == null || (apiKey == null || apiKey.isEmpty()) ||
        !checker.isAuthorized(apiKey.get(0), application)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to access this resource");
        }
        return chain.filter(exchange);
    }

    //This filter will be executed the last in order having the lowest precedence, in order to not interfere with any of by default apigw filters
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
