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

    public boolean existsById(Integer id) {
        return countryRepository.existsById(id);
    }

    public Country getById(Integer id) {
        return countryRepository.getById(id);
    }

    public Country findByName(String name){ //as. Name are unique?
        return countryRepository.findFirstByNameAndActive(name,(byte)1);
    }
}
