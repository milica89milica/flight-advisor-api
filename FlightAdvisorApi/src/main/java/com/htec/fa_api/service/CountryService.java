package com.htec.fa_api.service;


import com.htec.fa_api.model.Country;
import com.htec.fa_api.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll() {
        return countryRepository.getAllByActive((byte) 1);
    }

    public Country findByName(String name){
        return countryRepository.findByNameAndActive(name, (byte) 1);
    }
}
