package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.model.extended.RouteExtended;
import com.htec.fa_api.service.RouteService;
import com.htec.fa_api.util.CsvReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<Route>> uploadMultipart(@RequestParam("routes") MultipartFile file) throws IOException, HttpException {
        List<RouteExtended> routesExtended = CsvReader.read(RouteExtended.class, file.getInputStream());
        return new ResponseEntity<List<Route>>(routeService.saveAll(routesExtended), HttpStatus.CREATED);
    }
}
