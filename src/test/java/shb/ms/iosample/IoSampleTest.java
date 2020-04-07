package shb.ms.iosample;

import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, properties = {"server.port=8080"})
public class IoSampleTest {

    private static final String THREE_SECOND_URL = "http://localhost:8080/3second";
    private static final int LOOP_COUNT = 100;

    private final WebClient webClient = WebClient.create();
    private final CountDownLatch count = new CountDownLatch(LOOP_COUNT);

    @Before
    public void setup() {
        System.setProperty("reactor.netty.ioWorkerCount", "100");
    }

    @Test
    @Disabled("skipp:p")
    public void blocking1() {
        final RestTemplate restTemplate = new RestTemplate();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < LOOP_COUNT; i++) {
            System.out.println("loop count : " + (i + 1));
            final ResponseEntity<String> response =
                    restTemplate.exchange(THREE_SECOND_URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            assertThat(response.getBody()).contains("success");
        }

        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    @Test
    @Disabled("skipp:p")
    public void blocking2() throws InterruptedException {
        final AsyncRestTemplate restTemplate = new AsyncRestTemplate();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < LOOP_COUNT; i++) {
            System.out.println("loop count : " + (i + 1));
            final ListenableFuture<ResponseEntity<String>> response =
                    restTemplate.exchange(THREE_SECOND_URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);

            response.addCallback(result -> {
                count.countDown();
                System.out.println(result.getBody());
            }, ex -> {
                System.out.println(ex);
            });
        }


        count.await(30, TimeUnit.SECONDS);
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    @Test
    @Disabled("skipp:p")
    public void nonBlocking1() throws InterruptedException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        this.webClient
                .get()
                .uri(THREE_SECOND_URL)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(System.out::println)
                .subscribe();

        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeSeconds());

        Thread.sleep(5000);
    }

    @Test
    @Disabled("skipp:p")
    public void thread() {
        System.out.println(1);

        new Thread(() -> System.out.println(2)).start();

        System.out.println(3);
    }

    @Test
    @Disabled("skipp:p")
    public void nonBlocking2() throws InterruptedException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        this.webClient
                .get()
                .uri(THREE_SECOND_URL)
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .subscribe(it -> {
                    stopWatch.stop();
                    System.out.println(stopWatch.getTotalTimeSeconds());
                });

        Thread.sleep(5000);
    }

    @Test
    public void nonBlocking3() throws InterruptedException {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < LOOP_COUNT; i++) {
            System.out.println("loop count : " + (i + 1));
            this.webClient
                    .get()
                    .uri(THREE_SECOND_URL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(it -> {
                        count.countDown();
                        System.out.println(it);
                    });
        }

        count.await(50, TimeUnit.SECONDS);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
