package com.htec.fa_api.controller;


import com.htec.fa_api.model.User;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.service.UserGroupService;
import com.htec.fa_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserService userService;

    public UserGroupController(UserGroupService userGroupService, UserService userService) {
        this.userGroupService = userGroupService;
        this.userService = userService;
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
}

