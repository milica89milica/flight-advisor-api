package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.City;
import com.htec.fa_api.model.extended.AirportExtended;
import com.htec.fa_api.repository.AirportRepository;
import com.htec.fa_api.util.AirportType;
import com.htec.fa_api.util.DaylightSavingsTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportService {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final MessageSource messageSource;

    private final AirportRepository airportRepository;
    private final CityService cityService;

    public AirportService(MessageSource messageSource, AirportRepository airportRepository, CityService cityService) {
        this.messageSource = messageSource;
        this.airportRepository = airportRepository;
        this.cityService = cityService;
    }

    public List<Airport> saveAll(List<AirportExtended> airportsExtended) throws HttpException {
        List<Airport> airports = new ArrayList<>();
        for (AirportExtended airportExtended : airportsExtended) {
            Airport airport = new Airport();
            airport.setId(airportExtended.getId()); //already suplied
            airport.setName(airportExtended.getName());
            airport.setIataCode(airportExtended.getIataCode());
            airport.setIcaoCode(airportExtended.getIcaoCode());
            airport.setDbTimezone(airportExtended.getTimeZone());
            City city = cityService.findByNameAndCountry(airportExtended.getCityName(), airportExtended.getCountryName());
            if (city != null) {
                airport.setCity(city);
                airport.setLatitude(airportExtended.getLatitude());
                airport.setLongitude(airportExtended.getLongitude());
                airport.setAltitude(airportExtended.getAltitude());
                airport.setType(AirportType.AIRPORT);
                airport.setDst(DaylightSavingsTime.valueOf(airportExtended.getDst()).toString());
                log.info("Airport has been successfully created: " + airport); //todo messages
                airports.add(airport);
            } else {
                log.error(messageSource.getMessage("notExists.city", null, null) + " for " + airport);
            }
        }
        return airportRepository.saveAll(airports);
    }

    public List<Airport> getAll() {
        return airportRepository.getAllByActive((byte) 1);
    }

    public Airport findById(Integer id) {
        return airportRepository.findByIdAndActive(id, (byte) 1);
    }
}
