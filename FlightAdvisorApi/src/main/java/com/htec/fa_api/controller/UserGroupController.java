package com.htec.fa_api.controller;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.User;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.service.UserGroupService;
import com.htec.fa_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestScope
@RequestMapping("/userGroup")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserService userService;
    private final LoggerService loggerService;

    public UserGroupController(UserGroupService userGroupService, UserService userService, LoggerService loggerService) {
        this.userGroupService = userGroupService;
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAll(){
        return new ResponseEntity<List<UserGroup>>(userGroupService.getAll(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable Integer id){
        return new ResponseEntity<List<User>>(userService.getAllByUserGroup(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserGroup> insert(@RequestBody @Valid UserGroup object) throws HttpException {
        UserGroup userGroup = userGroupService.insert(object);
        loggerService.logAction(userGroup,"CREATE","logger.create");
        return new ResponseEntity<UserGroup>(userGroup, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserGroup> update(@PathVariable("id") Integer id, @Valid @RequestBody UserGroup object) throws HttpException {
        UserGroup userGroup = userGroupService.update(object);
        loggerService.logAction(userGroup,"UPDATE","logger.update");
        return new ResponseEntity<UserGroup>(userGroup, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws HttpException {
        UserGroup userGroup = userGroupService.findById(id);
        userService.delete(id);
        loggerService.logAction(userGroup,"DELETE","logger.delete");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

