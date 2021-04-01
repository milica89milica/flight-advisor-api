package com.htec.fa_api.controller;

import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.service.TravelEstimateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/travel-estimate")
public class TravelEstimateController {

    private final TravelEstimateService travelEstimateService;

    public TravelEstimateController(TravelEstimateService travelEstimateService) {
        this.travelEstimateService = travelEstimateService;
    }

    @GetMapping
    @RequestMapping("/{sourceCityId}/{destinationCityId}")
    public ResponseEntity<List<Route>> getSuggestions(@PathVariable Integer sourceCityId, @PathVariable Integer destinationCityId) throws HttpException {
        return new ResponseEntity<>(travelEstimateService.getSuggestions(sourceCityId,destinationCityId), HttpStatus.OK);
    }
}
