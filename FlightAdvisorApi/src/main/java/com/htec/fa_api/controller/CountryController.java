package com.htec.fa_api.controller;

import com.htec.fa_api.model.Country;
import com.htec.fa_api.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestScope
@RequestMapping("/country")
@Tag(name = "country", description = "Country REST Controller")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "View data for all countries", description = "", tags = {"country"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
    @GetMapping
    public ResponseEntity<List<Country>> getAll() {
        return new ResponseEntity<>(countryService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "View data for selected country", description = "The EP returns information about the country whose identifier was passed as a parameter", tags = {"country"})
    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(countryService.getById(id), HttpStatus.OK);
    }
}
