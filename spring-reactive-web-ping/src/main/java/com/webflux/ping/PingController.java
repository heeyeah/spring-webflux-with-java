package com.webflux.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;


public class PingController {
//    private final AtomicInteger atomicInteger = new AtomicInteger();
//    private final AtomicInteger atomicInteger2 = new AtomicInteger();
//
//    @GetMapping("/3second")
//    public Mono<String> threeSecond() throws InterruptedException {
//        Thread.sleep(3000);
//        String result = "3second success - " + this.atomicInteger.incrementAndGet();
//        return Mono.just(result);
//    }
//
//    @GetMapping("/ping")
//    public Mono<String> ping() {
//        String result = "ping success - " + this.atomicInteger2.incrementAndGet();
//        return Mono.just(result);
//    }
}
