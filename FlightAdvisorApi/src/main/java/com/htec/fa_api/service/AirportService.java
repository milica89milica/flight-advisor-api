package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.City;
import com.htec.fa_api.model.Country;
import com.htec.fa_api.model.extended.AirportExtended;
import com.htec.fa_api.repository.AirportRepository;
import com.htec.fa_api.util.AirportType;
import com.htec.fa_api.util.DaylightSavingsTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@CacheConfig(cacheNames = {"airports"})
@Service
@PropertySource("classpath:application.properties")
public class AirportService {

    @Value("${application.upload.city}")
    private boolean uploadCity;

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final MessageSource messageSource;
    private final CountryService countryService;

    private final AirportRepository airportRepository;
    private final CityService cityService;

    public AirportService(MessageSource messageSource, AirportRepository airportRepository, CityService cityService, CountryService countryService) {
        this.messageSource = messageSource;
        this.airportRepository = airportRepository;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Airport> saveAll(List<AirportExtended> airportsExtended) throws HttpException {
        List<Airport> airports = new ArrayList<>();
        for (AirportExtended airportExtended : airportsExtended) {
            Airport airport = airportRepository.findByOpenFlightIdAndActive(airportExtended.getId(),(byte)1);
            if (airport == null) { //airport is not already uploaded
                City city = cityService.findByNameAndCountry(airportExtended.getCityName(), airportExtended.getCountryName());
                if (city == null) {
                    if (uploadCity) {
                        Country country = countryService.findByName(airportExtended.getCountryName());
                        if (country != null) {
                            city = cityService.insert(new City(airportExtended.getCityName(), country, ""));
                            log.info(messageSource.getMessage("created.city", null, null) + " for " + airportExtended);
                        } else {
                            log.error(messageSource.getMessage("notExists.country", null, null) + " for " + airportExtended);

                        }
                    } else {
                        log.error(messageSource.getMessage("notExists.city", null, null) + " for " + airportExtended);
                    }
                }
                if (city != null) {
                    airport = new Airport();
                    airport.setOpenFlightId(airportExtended.getId());
                    airport.setName(airportExtended.getName());
                    airport.setIataCode(airportExtended.getIataCode());
                    airport.setIcaoCode(airportExtended.getIcaoCode());
                    airport.setDbTimezone(airportExtended.getTimeZone());
                    airport.setCity(city);
                    airport.setLatitude(airportExtended.getLatitude());
                    airport.setLongitude(airportExtended.getLongitude());
                    airport.setAltitude(airportExtended.getAltitude());
                    airport.setType(AirportType.AIRPORT.toString());
                    if (airportExtended.getDst() == null) {
                        airportExtended.setDst("N"); //work around
                    }
                    airport.setDst(DaylightSavingsTime.valueOf(airportExtended.getDst()).toString());
                    airport.setUtcTimeOffset(airportExtended.getUtcTimeOffset());
                    log.info(messageSource.getMessage("created.airport", null, null) + " for " + airportExtended);
                    airports.add(airport);
                }
            } else {
                log.info(messageSource.getMessage("alreadyExists.airport", null, null) + " for " + airportExtended);
            }

        }
        return airportRepository.saveAll(airports);
    }


    @Cacheable
    public List<Airport> getAll() {
        return airportRepository.getAllByActive((byte) 1);
    }

    @Cacheable(key = "#id")
    public Airport findById(Integer id) {
        return airportRepository.findByIdAndActive(id, (byte) 1);
    }

    @Cacheable(key = "#openFlightId")
    public Airport findByOpenFlightId(Integer openFlightId) {
        return airportRepository.findByOpenFlightIdAndActive(openFlightId, (byte) 1);
    }
    @Cacheable(key = "#cityId")
    public List<Airport> getAllByCity(Integer cityId) {
        return airportRepository.getAllByCityIdAndActive(cityId, (byte) 1);
    }

}
