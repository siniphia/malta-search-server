package com.malta.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class MaltaAdapterApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-post");
        SpringApplication.run(MaltaAdapterApplication.class, args);
    }
}
