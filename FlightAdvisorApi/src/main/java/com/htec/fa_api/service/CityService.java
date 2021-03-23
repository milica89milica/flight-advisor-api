package com.htec.fa_api.service;


import com.htec.fa_api.model.City;
import com.htec.fa_api.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll() {
        return cityRepository.getAllByActive((byte) 1);
    }

    public City findByNameAndCountry(String name, String countryName) {
        return cityRepository.findByNameAndCountryNameAndActive(name, countryName, (byte) 1);

    }
}
