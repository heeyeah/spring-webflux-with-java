package com.mvc.ping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import com.mashape.unirest.http.Unirest;
import com.mvc.shb.ShbEmployee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
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

    static AsyncRestTemplate template = new AsyncRestTemplate();

    @GetMapping("/mvc/{userId}")
    public ShbEmployee performHttpClientInMVC(@PathVariable String userId) {

        log.info("=>[{}] /mvc/{}", atomicInteger.incrementAndGet(), userId);

        ListenableFuture<ResponseEntity<ShbEmployee>> future = template.getForEntity("http://localhost:9000/employee/" + userId, ShbEmployee.class);

        ResponseEntity<ShbEmployee> responseEntity = null;
        try {
            responseEntity = future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("{}", e);
        }

        return responseEntity.getBody();
    }

    private static String API_URL = "http://localhost:9000/employee/";

    @GetMapping("/unirest/{userId}")
    public ShbEmployee performUnirestHttpClientInMVC(@PathVariable String userId) {

        log.info("=> /unirest/{}", userId);
        Future<HttpResponse<JsonNode>> future = Unirest.get(API_URL + userId)
                .asJsonAsync();

        HttpResponse<JsonNode> res= null;
        try {
            res = future.get();
        } catch (Exception e) {
          log.error("{}",e);
        }

        JsonNode resJson = res.getBody();

        ObjectMapper mapper = new ObjectMapper();

        ShbEmployee em = null;
        try {
            em = mapper.readValue(resJson.toString(), ShbEmployee.class);
        } catch (JsonProcessingException e) {
            log.error("{}",e);
        }


        return em;
    }
}
