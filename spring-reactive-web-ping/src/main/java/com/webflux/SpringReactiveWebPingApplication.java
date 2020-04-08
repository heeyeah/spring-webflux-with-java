package com.webflux;

import com.webflux.ping.EventLoopNettyCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringReactiveWebPingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveWebPingApplication.class, args);
	}

	@Bean
	public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
		NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
		webServerFactory.addServerCustomizers(new EventLoopNettyCustomizer());
		return  webServerFactory;
	}
}
