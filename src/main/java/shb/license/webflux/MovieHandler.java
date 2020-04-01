package shb.license.webflux;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Description("routing function 방식 - handler")
public class MovieHandler {

    public Mono<ServerResponse> defaultMovieHandler(ServerRequest request) {
        Mono<Movie> sample = Mono.just(new Movie(0, "Lala land", "Romance♡"));
        return ServerResponse.ok().body(sample, Movie.class);
    }

    public Mono<ServerResponse> favoriteMovieHandler(ServerRequest request) {
        Movie best = new Movie(1, "♬ Lala land", "Romance♡");
        Movie second = new Movie(2, "♬ Midnight in Paris", "Romance");
        Movie third = new Movie(3, "♬ Inception", "SF");

        return ServerResponse.ok().body(Flux.just(best, second, third), Movie.class);
    }
}
