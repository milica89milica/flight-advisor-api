package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.User;
import com.htec.fa_api.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestScope
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final LoggerService loggerService;

    private final MessageSource messageSource;

    public UserController(UserService userService, LoggerService loggerService, MessageSource messageSource) {
        this.userService = userService;
        this.loggerService = loggerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User object) throws HttpException {
        User user = userService.insert(object);
        loggerService.logAction(user, "CREATE", "logger.create");
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @Valid @RequestBody User object) throws HttpException {
        User user = userService.update(object);
        loggerService.logAction(user, "UPDATE", "logger.update");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws HttpException {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        loggerService.logAction(user, "DELETE", "logger.delete");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
