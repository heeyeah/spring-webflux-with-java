package com.webflux.ping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routes(WebFluxHandler handler) {
        return RouterFunctions.route(GET("/ping"), handler::pingHandler)
                .andRoute(GET("/3second"), handler::threeSecondHandler);
    }
}
