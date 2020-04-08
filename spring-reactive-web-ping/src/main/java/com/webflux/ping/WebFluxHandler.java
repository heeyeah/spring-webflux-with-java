package com.webflux.ping;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebFluxHandler {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final AtomicInteger atomicInteger2 = new AtomicInteger();

    public Mono<ServerResponse> threeSecondHandler(ServerRequest request) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = "3second success - " + this.atomicInteger.incrementAndGet();
        return ServerResponse.ok().body(Mono.just(result), String.class);
    }

    public Mono<ServerResponse> pingHandler(ServerRequest request) {
        String result = "ping success - " + this.atomicInteger2.incrementAndGet();

        return ServerResponse.ok().body(Mono.just(result), String.class);
    }
}
