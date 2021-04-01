package com.htec.fa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan({"com.htec.fa_api.util"})
@EnableScheduling
@EnableConfigurationProperties
@EntityScan("com.htec.fa_api")
@EnableJpaRepositories(basePackages = {"com.htec.fa_api"})
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
@SpringBootApplication
public class FlightAdvisorApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApiApplication.class, args);
    }

}
