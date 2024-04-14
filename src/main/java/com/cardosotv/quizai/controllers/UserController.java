package com.cardosotv.quizai.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.model.DTO.UserDTO;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    // https://www.baeldung.com/spring-rest-openapi-documentation (documentation about OpenAPI and Swagger)
    @Operation(summary = "Endpoind to GET all Users from database QuizAI."
         , description = "This endpoint retrieves the a list of users active.")
    @ApiResponse(responseCode = "200"
                , description = "Users found!"
                , content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "0") int page
                                                , @RequestParam(defaultValue = "10") int size
                                                , @RequestHeader("token") String token){     
        List<User> users;                                            
        try {
            users = this.userService.listAllUsers(page, size);
        } catch (Throwable t) {
            throw HandleException.handleException(t, null, "User");
        }                                                    
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getAllUsersActive(){
        List<User> users;
        try {
            users = this.userService.listAllUsersAtive();
        } catch (Throwable t) {
            throw t;
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable UUID userID 
                                        , @RequestHeader("token") String token) {
        // instanciate a User object
        UserDTO user;
        try {
            // try find out the user by ID
            user = this.userService.getUserByIdDTO(userID);
        } catch (Throwable t) {
            // if had any exception handling it.
            throw HandleException.handleException(t, userID, "User");
        }
        // return the user founded by response
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody @Valid User user
                                        , @RequestHeader("token") String token){
        Object result;
        try {
            result = this.userService.createUser(user, token);
        } catch (Throwable t) {
            throw HandleException.handleException(t, user, "User");
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@PathVariable UUID userID
                        , @RequestHeader("token") String token) {
        try {
            this.userService.deleteUser(userID, token);
        } catch (Throwable t) {
            throw HandleException.handleException(t, userID, "User");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user
                                        , @RequestHeader("token") String token){
        User userUpdated;
        try { 
            userUpdated = this.userService.updateUser(user, token);
        } catch (Throwable t) {
            throw HandleException.handleException(t, user, "User");
        }
        return ResponseEntity.ok(userUpdated);
    }

}
