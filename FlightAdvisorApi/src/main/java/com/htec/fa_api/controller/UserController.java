package com.htec.fa_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.model.User;
import com.htec.fa_api.security.AuthenticationFacade;
import com.htec.fa_api.service.UserService;
import com.htec.fa_api.util.ActionType;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestScope
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final LoggerService loggerService;

    private final MessageSource messageSource;
    private final AuthenticationFacade authenticationFacade; //todo

    public UserController(UserService userService, LoggerService loggerService, MessageSource messageSource, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.loggerService = loggerService;
        this.messageSource = messageSource;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User object) throws HttpException {
        User user = userService.insert(object);
        loggerService.logAction(user, ActionType.CREATE.name(), "logger.create");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @Valid @RequestBody User object) throws HttpException {
        User user = userService.update(object);
        loggerService.logAction(user, ActionType.UPDATE.name(), "logger.update");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) throws HttpException {
        User user = userService.delete(id);
        loggerService.logAction(user, ActionType.DELETE.name(), "logger.delete");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //comments for currently logged user
    @GetMapping
    @RequestMapping("/comments") //OK
    public ResponseEntity<List<Comment>> getAllComments() {
        //retrieve user info
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        return new ResponseEntity<>(user.getComments(), HttpStatus.OK);
    }

    //nLatest comments for currently logged user
    @GetMapping
    @RequestMapping("/comments/{nLatest}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer nLatest) {
        //retrieve user info
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);

        return new ResponseEntity<>(user.getComments(nLatest), HttpStatus.OK);
    }

    //comments for concrete user (for ADMIN)
    @GetMapping
    @RequestMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsForUser(@PathVariable Integer id) throws HttpException {
        //retrieve user info
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            if (!user.isPresent()) {
                throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(user.get().getComments(), HttpStatus.OK);
    }

    @PatchMapping
    @RequestMapping("/changePassword")
    public ResponseEntity<User> updatePassword(@RequestBody Map<String, String> object) {
        User userToBePatched = new ObjectMapper().convertValue(object, User.class);
        User user = userService.updatePassword(userToBePatched.getPassword());
        loggerService.logAction(user, ActionType.PASSWORD_CHANGE.name(), "logger.update");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
