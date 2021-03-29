package com.htec.fa_api.controller;

import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.City;
import com.htec.fa_api.service.AirportService;
import com.htec.fa_api.service.CityService;
import com.htec.fa_api.service.RouteService;
import com.htec.fa_api.util.GraphSearcher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/travel-estimate")
public class TravelEstimateController {

    private final RouteService routeService;
    private final CityService cityService;
    private final MessageSource messageSource;
    private final AirportService airportService;


    private final GraphSearcher graphSearcher;

    public TravelEstimateController(RouteService routeService, CityService cityService, MessageSource messageSource, AirportService airportService, GraphSearcher graphSearcher) {
        this.routeService = routeService;
        this.cityService = cityService;
        this.messageSource = messageSource;
        this.airportService = airportService;
        this.graphSearcher = graphSearcher;
    }

    @GetMapping
    @RequestMapping("/{sourceCityId}/{destinationCityId}")
    public ResponseEntity<List<Airport>> getData(@PathVariable Integer sourceCityId, @PathVariable Integer destinationCityId) throws HttpException {
        List<List<Integer>> suggestions = new ArrayList<>();
        List<Airport> matches = new ArrayList<>();
        Optional<City> sourceCity = cityService.findById(sourceCityId);
        if (!sourceCity.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        Optional<City> destinationCity = cityService.findById(destinationCityId);
        if (!destinationCity.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        //todo: city can have more than one airport - loop
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
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
