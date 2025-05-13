package com.assignment.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Value("${task.management.path}")
    private String taskManagementPath;
    @Value("${auth.path}")
    private String authPath;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.path(taskManagementPath)
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://TASK-MANAGEMENT-SERVICE")
                )
                .route(r -> r.path(authPath)
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ASSIGNMENT-AUTH"))
                .build();
    }
}
