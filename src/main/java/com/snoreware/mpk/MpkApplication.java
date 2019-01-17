package com.snoreware.mpk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MpkApplication {
    public static void main(String[] args) {
        SpringApplication.run(MpkApplication.class, args);
    }
}

