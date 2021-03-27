package com.htec.fa_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.service.CityService;
import com.htec.fa_api.service.CommentService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CityService cityService;
    private final LoggerService loggerService;
    private final MessageSource messageSource;

    public CommentController(CommentService commentService, CityService cityService, LoggerService loggerService, MessageSource messageSource) {
        this.commentService = commentService;
        this.cityService = cityService;
        this.loggerService = loggerService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<Comment> insert(@RequestBody Comment object) throws HttpException {
        Comment comment = commentService.insert(object);
        loggerService.logAction(comment, "CREATE", "logger.create");
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/all") //UC: admin can see all
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
    }

    //it is not possible to change city, or user
    @PostMapping(value = "/{id}")
    public ResponseEntity<Comment> update(@PathVariable("id") Integer id, @Valid @RequestBody Comment object) throws HttpException {
        Optional<Comment> comment = commentService.findById(id);
        if (!comment.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.comment", null, null), HttpStatus.NOT_FOUND);
        }
        if (!comment.get().getCity().equals(object.getCity())) {
            throw new HttpException(messageSource.getMessage("cantUpdate.city", null, null), HttpStatus.NOT_ACCEPTABLE);
        }
        if (!comment.get().getUser().equals(object.getUser())) {
            throw new HttpException(messageSource.getMessage("cantUpdate.user", null, null), HttpStatus.NOT_ACCEPTABLE);
        }
        Comment updated = commentService.update(object);
        loggerService.logAction(updated, "UPDATE", "logger.update");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Comment> partialUpdate(@PathVariable("id") Integer id, @RequestBody Map<String, String> object) throws HttpException {
        Comment toBePatched = new ObjectMapper().convertValue(object, Comment.class);
        Comment updated = commentService.partialUpdate(toBePatched);
        loggerService.logAction(updated, "UPDATE", "logger.update");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Integer id) throws HttpException {
        Comment deleted = commentService.delete(id);
        loggerService.logAction(deleted, "DELETE", "logger.delete");
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }


}
