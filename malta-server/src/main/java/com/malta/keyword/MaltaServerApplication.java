package com.malta.keyword;

import com.malta.post.service.PostService;
import com.malta.post.web.PostController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ComponentScan(basePackages = {"com.malta.post.web", "com.malta.post.service", "com.malta.keyword"})
@SpringBootApplication
public class MaltaServerApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-post,application-keyword");
        SpringApplication.run(MaltaServerApplication.class, args);
    }
}
