package com.cardosotv.quizai.model.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.error.UnauthorizedException;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.model.repositories.UserRepository;
import com.cardosotv.quizai.security.JWTUtil;
import org.springframework.data.domain.Page;
import com.cardosotv.quizai.error.*;

@Service
public class UserService {
    
    @Value("${com.cardoso.quizai.adminid}")
    private String adminID;

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<User> listAllUsers(int page, int size) {

        Page<User> users;
        try {
            // users = this.userRepository.findAll(PageRequest.of(page, size));
            users = this.userRepository.findAll(PageRequest.of(page, size));
        } catch (Throwable t) {
            throw t;
        }
        return users.getContent();
    }


    public List<User> listAllUsersAtive(){
        List<User> users;
        try {
            users = this.userRepository.findAllUsersActive();
        } catch (Throwable t) {
            throw t;
        }
        return users;
    }

    @SuppressWarnings("null")
    public User getUserByID(UUID userID){

        // search for the user in database by parameter infomed
        User user = this.userRepository.findById(userID).orElse(null);

        // check if it's null
        if(Objects.isNull(user)) { 
            // if it is thrown the NotfoundException
            throw new NotFoundException("User", userID);
        }

        // if not return the user founded 
        return user;
    }


    // method in charge of create the user on database
    @SuppressWarnings("null")
    public User createUser(User user, String token){
        
        // get the userId from token to use to create the new user        
        String idFromToken = JWTUtil.getUserIdFromToken(token);

        //try to create the user 
        user.setCreatedBy(UUID.fromString(idFromToken));
        user.setCreatedDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        try {
            this.userRepository.save(user);
        } catch (Throwable t) {
            //if error return excption 
            throw t;
        }
        // if not return the user object created
        return user;
    }

    @SuppressWarnings("null")
    public void deleteUser(UUID userId, String token){
        try {
            // verify if the user from UUID informed exist
            User user = this.userRepository.findById(userId).orElse(null);
            // if not throw the exception notfound
            if(Objects.isNull(user)) {
                throw new NotFoundException("User", userId);
            }
            // get the userID from token to register who deleted 
            UUID idDeletedBy = UUID.fromString(JWTUtil.getUserIdFromToken(token));

            // Update the field for logical delete 
            user.setDeletedDate(new Date());
            user.setDeletedBy(idDeletedBy);

            // Salve the object
            this.userRepository.save(user);

        } catch (Throwable t) {
            // if error thrown exception
            throw t;
        }
    }

    @SuppressWarnings("null")
    public User updateUser(User user, String token){

        User userDB;
        try {
            // find the user on database
            userDB = this.userRepository.findById(user.getId()).orElse(null);
            // if not found return NotFound exception
            if (Objects.isNull(userDB)) {
                throw new NotFoundException("User", user);
            }
            // if found update the atributes 
            userDB.updateObject(user, UUID.fromString(JWTUtil.getUserIdFromToken(token)));
            // salve the user updated 
            this.userRepository.save(userDB);
        } catch (Throwable t) {
            throw t;
        }
        return userDB;
    }

}
