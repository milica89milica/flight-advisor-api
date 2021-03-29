package com.htec.fa_api.repository;


import com.htec.fa_api.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    List<Country> getAllByActive(Byte active);
    Country findFirstByNameAndActive(String name, Byte active); //in phase testing

    Country getById(Integer id);

    Integer countByActive(byte active);

}
