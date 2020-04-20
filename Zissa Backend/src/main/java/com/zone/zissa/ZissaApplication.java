package com.zone.zissa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The ZissaApplication Class.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ZissaApplication {

    /**
     * The main Class for Zissa application.
     * 
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(ZissaApplication.class, args);
    }
}
