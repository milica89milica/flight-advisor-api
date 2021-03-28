package com.htec.fa_api.repository;


import com.htec.fa_api.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {

    List<Airline> getAllByActive(byte active);
    Airline findByOpenFlightIdAndActive(Integer openFlightId, byte active);
}
