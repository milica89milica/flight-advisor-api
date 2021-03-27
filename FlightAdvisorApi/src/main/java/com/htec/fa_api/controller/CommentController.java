package com.htec.fa_api.controller;

import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.service.CityService;
import com.htec.fa_api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CityService cityService;
    private final LoggerService loggerService;


    public CommentController(CommentService commentService, CityService cityService, LoggerService loggerService) {
        this.commentService = commentService;
        this.cityService = cityService;
        this.loggerService = loggerService;
    }

    @PostMapping
    public ResponseEntity<Comment> insert(@RequestBody Comment comment) throws HttpException {
        //todo
        return new ResponseEntity<>(null, null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<List<Comment>>(commentService.getAll(), HttpStatus.OK);
    }
}
