package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.City;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.service.AirportService;
import com.htec.fa_api.service.CityService;
import com.htec.fa_api.service.CommentService;
import com.htec.fa_api.service.CountryService;
import com.htec.fa_api.util.ActionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Tag(name = "city", description = "City REST Controller")
@RestController
@RequestScope
@RequestMapping("/city")
@Validated
public class CityController {

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    @Autowired
    LoggerService loggerService;
    private final MessageSource messageSource;
    private final CountryService countryService;
    private final CityService cityService;
    private final CommentService commentService;

    public CityController(MessageSource messageSource, CountryService countryService, CityService cityService, CommentService commentService) {
        this.messageSource = messageSource;
        this.countryService = countryService;
        this.cityService = cityService;
        this.commentService = commentService;
    }

    @Operation(summary = "View data for all cities", description = "", tags = {"city"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval"),})
    @GetMapping
    public ResponseEntity<List<City>> getAll() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }


    @Operation(summary = "Adding a new city", description = "By calling this EP it is possible to add a new city. it is necessary to pre-select the country.", tags = {"city"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was successful and the user can preview the inserted city"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping
    public ResponseEntity<City> insert(@RequestBody @Valid City object, Errors errors) throws HttpException {
        if (!countryService.existsById(object.getCountry().getId())) {
            throw new HttpException(messageSource.getMessage("notExists.country", null, null), HttpStatus.BAD_REQUEST);
        }
        City city = cityService.insert(object);
        loggerService.logAction(city, ActionType.CREATE.name(), "logger.create");
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @Operation(summary = "Change data about the selected city", description = "By calling this EP it is possible to add a new city. it is necessary to pre-select the country.", tags = {"city"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was successful and the user can preview the updated city"),
            @ApiResponse(responseCode = "400", description = "The code 400 is returned if the identifier of the selected country does not exist in the database as valid"),
            @ApiResponse(responseCode = "404", description = "The provided city id does not exist in the database")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<City> update(@PathVariable("id") Integer id, @Valid @RequestBody City object) throws HttpException {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        City updated = cityService.update(object);
        loggerService.logAction(updated, ActionType.UPDATE.name(), "logger.update");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    @Operation(summary = "Delete the selected city", description = "A soft delete is performed", tags = {"city"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was successful and the user can preview the deleted city"),
            @ApiResponse(responseCode = "404", description = "The provided city id does not exist in the database")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<City> delete(@PathVariable Integer id) throws HttpException {
        City city = cityService.delete(id);
        loggerService.logAction(city, ActionType.DELETE.name(), "logger.delete");
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    //comments for concrete city
    @GetMapping
    @RequestMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer id) throws HttpException {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city.get().getCommentList(), HttpStatus.OK);
    }

    //get latest n comments for concrete city
    @GetMapping
    @RequestMapping("/{id}/comments/{nLatest}")
    public ResponseEntity<List<Comment>> getLatestComments(@PathVariable Integer id, @PathVariable Integer nLatest) throws HttpException {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        List<Comment> list = city.get().getCommentList(nLatest);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //get all comments for cities that match
    @GetMapping
    @RequestMapping("/search/{pattern}/comments")
    public ResponseEntity<List<City>> getByNameWithComments(@PathVariable String pattern) {
        return new ResponseEntity<>(cityService.getByNameWithComments(pattern), HttpStatus.OK);
    }


//    @GetMapping
//    @RequestMapping("/search/{name}/comments/{nLatest}") //todo

//    @GetMapping
//    @RequestMapping("/{id}/comments") //todo



}
