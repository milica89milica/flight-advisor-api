package com.htec.fa_api.logger;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logger")
public class LoggerController {

    private final LoggerService loggerService;

    public LoggerController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @GetMapping
    public ResponseEntity<List<Logger>> getAll(){
        return new ResponseEntity<List<Logger>>(loggerService.getAll(), HttpStatus.OK);
    }
}
