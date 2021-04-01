package com.htec.fa_api.repository;

import com.htec.fa_api.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> getAllByActive(Byte active);

    Route getFirstBySourceAirportIdAndDestinationAirportIdAndActiveOrderByPriceAsc(Integer sourceAirportId, Integer destinationAirportId, byte active);

}
