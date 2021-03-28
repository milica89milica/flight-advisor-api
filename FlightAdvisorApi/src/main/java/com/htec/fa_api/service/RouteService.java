package com.htec.fa_api.service;

import com.htec.fa_api.model.AircraftType;
import com.htec.fa_api.model.Airline;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.model.extended.RouteExtended;
import com.htec.fa_api.repository.RouteRepository;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.htec.fa_api.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final RouteRepository routeRepository;

    private final AirportService airportService;

    private MessageSource messageSource;

    public RouteService(RouteRepository routeRepository, AirportService airportService, MessageSource messageSource) {
        this.routeRepository = routeRepository;
        this.airportService = airportService;
        this.messageSource = messageSource;
    }

    public List<Route> saveAll(List<RouteExtended> routesExtended) throws HttpException {
        List<Route> routes = new ArrayList<>();
        for (RouteExtended routeExtended : routesExtended) {
            Route route = new Route();
            route.setPrice(routeExtended.getPrice());

            Airport sourceAirport = airportService.findById(routeExtended.getSourceAirportId());
            if (sourceAirport == null) {
                throw new HttpException(messageSource.getMessage("{notExists.sourceAirport}", null, null), HttpStatus.NOT_FOUND);
            }
            route.setSourceAirport(sourceAirport);

            Airport destinationAirport = airportService.findById(routeExtended.getDestinationAirportId());
            if (destinationAirport == null) {
                throw new HttpException(messageSource.getMessage("{notExists.destinationAirport}", null, null), HttpStatus.NOT_FOUND);
            }
            route.setDestinationAirport(destinationAirport);

            //airline
            Airline airline = new Airline();
            airline.setId(routeExtended.getAirlineId());
            airline.setIataCode(routeExtended.getAirlineCode());
            route.setAirline(airline);

            if (routeExtended.getCodeshare().isEmpty()) {
                route.setCodeshare((byte) 0);
            } else {
                route.setCodeshare((byte) 1);
            }

            AircraftType equipment = new AircraftType();
            equipment.setIcaoCode(routeExtended.getEquipmentCode());
            route.setEquipment(equipment);

            route.setStops(routeExtended.getStops());

            log.info(messageSource.getMessage("{logger.create}", null, null), route);
            routes.add(route);

        }
        return routeRepository.saveAll(routes);
    }

    public List<Route> getAll() {
        return routeRepository.getAllByActive((byte) 1);
    }
}
