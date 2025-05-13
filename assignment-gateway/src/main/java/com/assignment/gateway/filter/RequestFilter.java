package com.assignment.gateway.filter;

import com.assignment.core.utils.SimpleLogger;
import com.assignment.gateway.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class RequestFilter implements GlobalFilter, Ordered {
    private static final String SECRET_HEADER_NAME = "X-Secret-Header";
    private static final String SECRET_HEADER_VALUE = "qpfK6gr0CDVngaIKEj84";

    private static final String X_HEADER_USER_NAME = "X-User-Name";
    private static final String X_HEADER_USER_ID = "X-User-Id";
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        SimpleLogger.info("================= Start Filter Request to [{}] with request Id : [{}] =================",request.getURI().getPath(),request.getId());
        ServerHttpResponse response = exchange.getResponse();
        if (request.getMethod().equals(HttpMethod.OPTIONS)) {
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }
        Mono<Void> result = handleOtherHttpMethod(exchange, chain, request, response);
        SimpleLogger.info("================= End Filter Request to [{}] with request Id : [{}] =================",request.getURI().getPath(),request.getId());
        return result;
    }
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private Mono<Void> handleOtherHttpMethod(ServerWebExchange exchange, GatewayFilterChain chain, ServerHttpRequest request, ServerHttpResponse response) {
        String userName = null;
        String userId = null;
        if (isNeedSecure().test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request");
            }
            if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                userName = jwtUtils.getUsernameFromRequest(request);
                userId = jwtUtils.getUserIdFromRequest(request);
            } else {
                return this.onError(exchange, "Token invalid");
            }

        }
        ServerHttpRequest build = request.mutate()
                .header(SECRET_HEADER_NAME, SECRET_HEADER_VALUE)
                .header(X_HEADER_USER_NAME, userName)
                .header(X_HEADER_USER_ID, userId)
                .build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    private Predicate<ServerHttpRequest> isNeedSecure() {
        List<String> permitUrls = List.of("/task-management/users/registration", "/auth/login","/auth/refresh-token");
        return request -> permitUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private Mono<Void> onError(ServerWebExchange serverWebExchange, String error) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        SimpleLogger.error(error);
        return response.setComplete();
    }

}
