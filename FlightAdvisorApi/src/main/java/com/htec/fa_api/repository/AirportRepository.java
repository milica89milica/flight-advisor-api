package com.htec.fa_api.repository;


import com.htec.fa_api.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> getAllByActive(Byte active);

    Airport findByIdAndActive(Integer id, Byte active);

    Airport getByIataCodeAndIcaoCodeAndActive(String iataCode, String icaoCode, Byte active);


}
