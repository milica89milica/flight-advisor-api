package com.htec.fa_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.logger.LoggerService;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.model.User;
import com.htec.fa_api.service.UserService;
import com.htec.fa_api.util.ActionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "user", description = "User REST Controller")
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


    @Operation(summary = "View data for all users", description = "", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }


    @Operation(summary = "Add a new user",
            description = "By calling this EP it is possible to add a new user. it is necessary to pre-select the user group.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The operation was successful and admin can preview the inserted user"),
            @ApiResponse(responseCode = "400", description = "Bad request: \n" +
                    "The username must be unique and the selected user group must be valid")})
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User object) throws HttpException {
        User user = userService.insert(object);
        loggerService.logAction(user, ActionType.CREATE.name(), "logger.create");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @Operation(summary = "Change data about the selected user",
            description = "By calling this EP it is possible to change data for the selected user.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was successful and the admin can preview the updated user"),
            @ApiResponse(responseCode = "404", description = "The provided user id does not exist in the database")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @Valid @RequestBody User object) throws HttpException {
        User user = userService.update(object);
        loggerService.logAction(user, ActionType.UPDATE.name(), "logger.update");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Delete the selected user", description = "A soft delete is performed", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was successful and the admin can preview the deleted user"),
            @ApiResponse(responseCode = "404", description = "The provided user id does not exist in the database")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) throws HttpException {
        User user = userService.delete(id);
        loggerService.logAction(user, ActionType.DELETE.name(), "logger.delete");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @Operation(summary = "View all comments for currently logged in user", description = "", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
    //comments for currently logged user
    @GetMapping
    @RequestMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        //retrieve user info
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        return new ResponseEntity<>(user.getComments(), HttpStatus.OK);
    }

    @Operation(summary = "View n latest comments for the currently logged in user", description = "", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
    //nLatest comments for currently logged user
    @GetMapping
    @RequestMapping("/comments/{nLatest}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer nLatest) {
        //retrieve user info
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);

        return new ResponseEntity<>(user.getComments(nLatest), HttpStatus.OK);
    }

    @Operation(summary = "View all comments for the selected user", description = "", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
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

    @Operation(summary = "Change password for the currently logged in user",
            description = "By calling this EP it is possible to change password for the currently logged in user.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The password was successfully changed and the user can use it for the next login"),
            @ApiResponse(responseCode = "400", description = "Bad request: new password must be supplied")})
    @PatchMapping
    @RequestMapping("/changePassword")
    public ResponseEntity<User> updatePassword(@RequestBody Map<String, String> object) throws HttpException {
        User userToBePatched = new ObjectMapper().convertValue(object, User.class);
        if(userToBePatched.getPassword()==null){
            throw new HttpException(messageSource.getMessage("notNull.password", null, null), HttpStatus.BAD_REQUEST);
        }
        User user = userService.updatePassword(userToBePatched.getPassword());
        loggerService.logAction(user, ActionType.PASSWORD_CHANGE.name(), "logger.update");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @Operation(summary = "View account info for currently logged in user", description = "", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval")})
    //get data about currently logged in user
    @GetMapping("/about")
    public ResponseEntity<User> getInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
