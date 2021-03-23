package com.htec.fa_api.service;


import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.extended.AirportExtended;
import com.htec.fa_api.repository.AirportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportService {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final AirportRepository airportRepository;
    private final CityService cityService;
    private final CountryService countryService;

    public AirportService(AirportRepository airportRepository, CityService cityService, CountryService countryService) {
        this.airportRepository = airportRepository;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    public List<Airport> saveAll(List<AirportExtended> airportsExtended) {
        //todo
        return new ArrayList<>();
    }

    public List<Airport> getAll() {
        return airportRepository.getAllByActive((byte) 1);
    }

    public Airport findById(Integer id) {
        return airportRepository.findByIdAndActive(id, (byte) 1);
    }
}
