package com.htec.fa_api.util;

import com.htec.fa_api.repository.CountryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CountryLoader implements ApplicationRunner {


    private final CountryRepository countryRepository;

    public CountryLoader(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void run(ApplicationArguments args) {
        if(countryRepository.countByActive((byte)1) == 0){
            countryRepository.saveAll(CountryGenerator.create());
        }
    }
}