package com.htec.fa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlightAdvisorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApiApplication.class, args);
    }
}
