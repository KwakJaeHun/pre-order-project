package com.jhkwak.preorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PreOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreOrderApplication.class, args);
    }

}
