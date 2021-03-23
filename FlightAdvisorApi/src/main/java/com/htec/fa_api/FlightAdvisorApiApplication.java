package com.htec.fa_api;

import com.htec.fa_api.repository.CountryRepository;
import com.htec.fa_api.util.CountryGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlightAdvisorApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(FlightAdvisorApiApplication.class, args);
        CountryRepository countryRepository = configurableApplicationContext.getBean(CountryRepository.class);
        countryRepository.saveAll(CountryGenerator.create());

    }
}
