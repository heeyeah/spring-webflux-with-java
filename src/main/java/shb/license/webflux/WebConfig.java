package shb.license.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableWebFlux
@Description("routing function 방식 - routing config")
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routes(MovieHandler handler) {
        return RouterFunctions.route(GET("/v2/movie"), handler::defaultMovieHandler)
                .andRoute(GET("/v2/movie/favorite"), handler::favoriteMovieHandler);
    }
}
