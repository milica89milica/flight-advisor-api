package com.htec.fa_api.service;


import com.htec.fa_api.model.Country;
import com.htec.fa_api.repository.CountryRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@CacheConfig(cacheNames = {"countries"})
@Service
public class CountryService {



    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Cacheable
    public List<Country> getAll() {
        return countryRepository.getAllByActive((byte) 1);
    }

    @Cacheable(key = "#id")
    public boolean existsById(Integer id) {
        return countryRepository.existsById(id);
    }

    @Cacheable(key = "#id")
    public Country getById(Integer id) {
        return countryRepository.getById(id);
    }

    @Cacheable(key = "#name")
    public Country findByName(String name) { //as. Name are unique?
        return countryRepository.findFirstByNameAndActive(name, (byte) 1);
    }
}
