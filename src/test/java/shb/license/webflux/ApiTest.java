package shb.license.webflux;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebFluxTest
@Import(value = MovieHandler.class)
public class ApiTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("webflux 라우팅테스트")
    public void testWebConfigRouter() {

        Movie responseBody = webClient.get().uri("/v2/movie").exchange()
                .expectStatus().isOk().expectBody(Movie.class).returnResult().getResponseBody();

        List<Movie> fluxBody = webClient.get().uri("/v2/movie/favorite").exchange()
                .expectStatus().isOk().expectBody(List.class).returnResult().getResponseBody();


        assertThat(responseBody.getId()).isEqualTo(0);
        assertThat(responseBody.getTitle()).isEqualTo("Lala land");
        assertThat(responseBody.getCategory()).isEqualTo("Romance♡");

        System.out.println(fluxBody.toString());
    }

    @Test
    @DisplayName("not supported post method")
    public void testFailExecutePostMethod() {
        //webClient.post().uri("/v2/movie").exchange().expectStatus().isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        webClient.post().uri("/v2/movie").exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("controller version")
    public void testControllerVersion() {
        Movie dftBody = webClient.get().uri("/v1/movie").exchange().expectBody(Movie.class).returnResult().getResponseBody();
        List<Movie> fvrtBody = webClient.get().uri("/v1/movie/favorite").exchange().expectBody(List.class).returnResult().getResponseBody();

        System.out.println(dftBody.toString());
        System.out.println(fvrtBody.toString());
    }
}
