package com.htec.fa_api.repository;


import com.htec.fa_api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> getAllByActive(Byte active);
 }
