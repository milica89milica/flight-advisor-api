package com.htec.fa_api.repository;


import com.htec.fa_api.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    List<Country> getAllByActive(Byte active);

}
