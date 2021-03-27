package com.htec.fa_api.controller;

import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.extended.AirportExtended;
import com.htec.fa_api.service.AirportService;
import com.htec.fa_api.util.CsvReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestScope
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<Airport>> uploadMultipart(@RequestParam("airports") MultipartFile file) throws IOException, HttpException {
        List<AirportExtended> airportsExtended = CsvReader.read(AirportExtended.class, file.getInputStream());
        return new ResponseEntity<List<Airport>>(airportService.saveAll(airportsExtended), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAll() {
        return new ResponseEntity<List<Airport>>(airportService.getAll(), HttpStatus.OK);
    }
}
