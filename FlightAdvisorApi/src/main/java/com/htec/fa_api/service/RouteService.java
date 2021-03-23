package com.htec.fa_api.service;

import com.htec.fa_api.model.Route;
import com.htec.fa_api.model.extended.RouteExtended;
import com.htec.fa_api.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.htec.fa_api.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final RouteRepository routeRepository;

    private final AirportService airportService;

    public RouteService(RouteRepository routeRepository, AirportService airportService) {
        this.routeRepository = routeRepository;
        this.airportService = airportService;
    }

    public List<Route> saveAll(List<RouteExtended> routesExtended) throws HttpException {
        //todo
        return new ArrayList<>();
    }
}
