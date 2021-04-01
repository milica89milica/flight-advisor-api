package com.htec.fa_api.service;

import com.htec.fa_api.model.AircraftType;
import com.htec.fa_api.model.Airline;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.model.extended.RouteExtended;
import com.htec.fa_api.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.htec.fa_api.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = {"routes"})
@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final RouteRepository routeRepository;

    private final AirportService airportService;

    private final MessageSource messageSource;
    private final AirlineService airlineService;

    public RouteService(RouteRepository routeRepository, AirportService airportService, MessageSource messageSource, AirlineService airlineService) {
        this.routeRepository = routeRepository;
        this.airportService = airportService;
        this.messageSource = messageSource;
        this.airlineService = airlineService;
    }

    public List<Route> saveAll(List<RouteExtended> routesExtended) throws HttpException {
        List<Route> routes = new ArrayList<>();
        for (RouteExtended routeExtended : routesExtended) {
            Route route = new Route(); //route identifier? (sourceId, destinationId, airplaneId)
            Airport sourceAirport = airportService.findByOpenFlightId(routeExtended.getSourceAirportId());
            if (sourceAirport != null) {
                Airport destinationAirport = airportService.findByOpenFlightId(routeExtended.getDestinationAirportId());
                if (destinationAirport != null) {
                    Airline airline = airlineService.findByOpenFlightId(routeExtended.getAirlineId());
                    if (airline != null) {
                        route.setSourceAirport(sourceAirport);
                        route.setDestinationAirport(destinationAirport);
                        route.setAirline(airline);
                        route.setPrice(routeExtended.getPrice());

                        if (routeExtended.getCodeshare().isEmpty()) {
                            route.setCodeshare((byte) 0);
                        } else {
                            route.setCodeshare((byte) 1);
                        }

                        AircraftType equipment = new AircraftType();
                        equipment.setIcaoCode(routeExtended.getEquipmentCode());
                        route.setEquipment(equipment);

                        route.setStops(routeExtended.getStops());

                        log.info(messageSource.getMessage("created.route", null, null) + " for " + route);
                        routes.add(route);
                    } else {
                        log.error(messageSource.getMessage("notExists.airline", null, null) + " for " + route);
                    }
                } else {
                    log.error(messageSource.getMessage("notExists.destinationAirport", null, null) + " for " + route);
                }

            } else {
                log.error(messageSource.getMessage("notExists.sourceAirport", null, null) + " for " + route);
            }

        }
        return routeRepository.saveAll(routes);
    }

    @Cacheable
    public List<Route> getAll() {
        return routeRepository.getAllByActive((byte) 1);
    }


    public Route getCheapest(Integer sourceAirportId, Integer destinationAirportId){
        return routeRepository.getFirstBySourceAirportIdAndDestinationAirportIdAndActiveOrderByPriceAsc(sourceAirportId,destinationAirportId,(byte)1);

    }
}
