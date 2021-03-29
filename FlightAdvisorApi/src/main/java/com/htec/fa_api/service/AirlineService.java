package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airline;
import com.htec.fa_api.model.Country;
import com.htec.fa_api.model.extended.AirlineExtended;
import com.htec.fa_api.repository.AirlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    private final CountryService countryService;

    private final MessageSource messageSource;

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    public AirlineService(AirlineRepository airlineRepository, MessageSource messageSource, CountryService countryService) {
        this.airlineRepository = airlineRepository;
        this.messageSource = messageSource;
        this.countryService = countryService;
    }

    public List<Airline> getAll() {
        return airlineRepository.getAllByActive((byte) 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Airline> saveAll(List<AirlineExtended> airlinesExtended) throws HttpException {
        List<Airline> airlines = new ArrayList<>();
        for (AirlineExtended airlineExtended : airlinesExtended) {
            Airline airline = airlineRepository.findByOpenFlightIdAndActive(airlineExtended.getOpenFlightId(), (byte) 1);
            if (airline == null) { //airline is not already uploaded
                airline = new Airline();
                airline.setName(airlineExtended.getName());
                airline.setOpenFlightId(airlineExtended.getOpenFlightId());
                if (airlineExtended.getIataCode() != null && !airlineExtended.getIataCode().equals("-")) { //todo check all iata and icao inputs
                    airline.setIataCode(airlineExtended.getIataCode());
                }
                if (airlineExtended.getIcaoCode() != null && !airlineExtended.getIcaoCode().equals("N/A")) {
                    airline.setIcaoCode(airlineExtended.getIcaoCode());
                }
                airline.setAlias(airlineExtended.getAlias());
                Country country = countryService.findByName(airlineExtended.getCountryName());
                if (country != null) {
                    airline.setCountry(country);
                }
                airlines.add(airline);
                log.info(messageSource.getMessage("created.airline", null, null) + " for " + airline);
            } else {
                log.error(messageSource.getMessage("alreadyExists.airline", null, null) + " for " + airlineExtended);
            }

        }
        return airlineRepository.saveAll(airlines);
    }

    public Airline findByOpenFlightId(Integer openFlightId) {
        return airlineRepository.findByOpenFlightIdAndActive(openFlightId, (byte) 1);
    }
}
