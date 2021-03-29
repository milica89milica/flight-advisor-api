package com.htec.fa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@EnableConfigurationProperties
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class FlightAdvisorApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApiApplication.class, args);
    }

}
