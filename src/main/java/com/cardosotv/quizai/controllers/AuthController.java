package com.cardosotv.quizai.controllers;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardosotv.quizai.error.UnauthorizedException;
import com.cardosotv.quizai.model.entities.Authorization;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.model.services.UserService;
import com.cardosotv.quizai.security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${com.cardoso.quizai.adminid}")
    private String adminID;

    @Autowired
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @SuppressWarnings("deprecation")
    @GetMapping
    public ResponseEntity<Object> getAuthorization(@RequestHeader("userId") String userID){

        // check if the user exists on database
        if (!userID.equals(adminID)){
            User user = this.userService.getUserByID(UUID.fromString(userID));
        
            // if not return Unathorizated Exception
            if(Objects.isNull(user)) {
                throw new UnauthorizedException();
            }
        }
        // if yes retorn the token as JWT Object
        String token = JWTUtil.generateToken(userID);

        Authorization auth = new Authorization(token);
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

}