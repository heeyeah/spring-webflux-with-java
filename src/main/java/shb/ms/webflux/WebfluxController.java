package shb.ms.webflux;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movie")
@Description("Controller 방식")
public class WebfluxController {

    @GetMapping
    public Mono<Movie> defaultMovie() {
        Movie movie = new Movie(0, "Lala land", "Romance");
        return Mono.just(movie);
    }

    @GetMapping("/favorite")
    public Flux<Movie> getFavoriteMovies() {
        Movie best = new Movie(1, "Lala land", "Romance");
        Movie second = new Movie(2, "Midnight in Paris", "Romance");
        Movie third = new Movie(3, "Inception", "SF");

        return Flux.just(best, second, third);
    }
}
