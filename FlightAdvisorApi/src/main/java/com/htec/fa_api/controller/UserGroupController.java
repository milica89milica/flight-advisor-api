package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.User;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.service.UserGroupService;
import com.htec.fa_api.service.UserService;
import com.htec.fa_api.util.ActionType;
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
@RequestMapping("/userGroup")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserService userService;
    private final LoggerService loggerService;
    private final MessageSource messageSource;

    public UserGroupController(UserGroupService userGroupService, UserService userService, LoggerService loggerService, MessageSource messageSource) {
        this.userGroupService = userGroupService;
        this.userService = userService;
        this.loggerService = loggerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAll(){
        return new ResponseEntity<>(userGroupService.getAll(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getAllByUserGroup(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserGroup> insert(@RequestBody @Valid UserGroup object) throws HttpException {
        UserGroup userGroup = userGroupService.insert(object);
        loggerService.logAction(userGroup, ActionType.CREATE.name(),"logger.create");
        return new ResponseEntity<>(userGroup, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserGroup> update(@PathVariable("id") Integer id, @Valid @RequestBody UserGroup object) throws HttpException {
        UserGroup userGroup = userGroupService.update(object);
        loggerService.logAction(userGroup,ActionType.UPDATE.name(),"logger.update");
        return new ResponseEntity<>(userGroup, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserGroup> delete(@PathVariable Integer id) throws HttpException {
        UserGroup userGroup = userGroupService.delete(id);
        loggerService.logAction(userGroup,ActionType.DELETE.name(),"logger.delete");
        return new ResponseEntity<>(userGroup, HttpStatus.OK);
    }
}

