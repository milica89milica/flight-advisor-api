package com.htec.fa_api.logger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, Integer> {
    @Query("FROM Logger l")
    List<Logger> getAll();

}
