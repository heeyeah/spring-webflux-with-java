package com.mvc.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PingController {

    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final AtomicInteger atomicInteger2 = new AtomicInteger();

    @GetMapping("/3second")
    public String threeSecond() throws InterruptedException {
        Thread.sleep(3000);
        return "3second success - " + this.atomicInteger.incrementAndGet();
    }

    @GetMapping("/ping")
    public String ping() {
        return "ping success - " + this.atomicInteger2.incrementAndGet();
    }
}
