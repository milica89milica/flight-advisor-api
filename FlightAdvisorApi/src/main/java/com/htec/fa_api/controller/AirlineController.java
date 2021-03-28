package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Airline;
import com.htec.fa_api.model.extended.AirlineExtended;
import com.htec.fa_api.service.AirlineService;
import com.htec.fa_api.util.CsvReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineService airlineService;


    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data") //put in generic controller
    public ResponseEntity<List<Airline>> uploadMultipart(@RequestParam("airlines") MultipartFile file) throws IOException, HttpException {
        List<AirlineExtended> airlinesExtended = CsvReader.read(AirlineExtended.class, file.getInputStream());
        return new ResponseEntity<>(airlineService.saveAll(airlinesExtended), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Airline>> getAll() {
        return new ResponseEntity<>(airlineService.getAll(), HttpStatus.OK);
    }
}
