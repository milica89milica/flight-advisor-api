package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.City;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.util.GraphSearcher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TravelEstimateService {
    private final CityService cityService;
    private final MessageSource messageSource;
    private final AirportService airportService;

    private final RouteService routeService;

    private final GraphSearcher graphSearcher;


    public TravelEstimateService(CityService cityService, MessageSource messageSource, AirportService airportService, RouteService routeService, GraphSearcher graphSearcher) {
        this.cityService = cityService;
        this.messageSource = messageSource;
        this.airportService = airportService;
        this.routeService = routeService;
        this.graphSearcher = graphSearcher;
    }

    public List<Route> getSuggestions(Integer sourceCityId, Integer destinationCityId) throws HttpException {
        List<List<Integer>> suggestions = new ArrayList<>();
        List<Route> result = new ArrayList<>();
        List<Airport> matches = new ArrayList<>();

        Optional<City> sourceCity = cityService.findById(sourceCityId);
        if (!sourceCity.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        Optional<City> destinationCity = cityService.findById(destinationCityId);
        if (!destinationCity.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }

        graphSearcher.defineConnections();

        List<Airport> sourceAirports = airportService.getAllByCity(sourceCityId); //todo; check if city has airport!
        List<Airport> destinationAirports = airportService.getAllByCity(destinationCityId);

        for (Airport sourceAirport : sourceAirports) {
            for (Airport destinationAirport : destinationAirports) {
                suggestions = graphSearcher.search(sourceAirport, destinationAirport);
            }
        }
        //take info
        for (int i = 0; i < suggestions.size(); i++) {
            List<Integer> path = suggestions.get(i);
            for (Integer node : path) {
                matches.add(airportService.findByOpenFlightId(node));
            }
        }

        for (int i = 0; i < matches.size(); i += 2) {
            result.add(routeService.getCheapest(matches.get(i).getId(), matches.get(i + 1).getId()));
        }

        return result;
    }

}
