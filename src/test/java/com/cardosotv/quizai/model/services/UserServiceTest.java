package com.cardosotv.quizai.model.services;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.repositories.UserRepository;
import com.cardosotv.quizai.security.JWTUtil;
import com.cardosotv.quizai.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    public User createMockUser() {
        return new User(new Date()
                    , UUID.randomUUID()
                    , "User Mock"
                    , "usermock@quizai.com.br"
                    , "0451123456"
                    , new Date()
                    , "123 Main St"
                    , 0
        );
    }

    @SuppressWarnings("null")
    @Test
    void getUserByID_ShouldReturnUser() {
        // arrange
        UUID userID = UUID.randomUUID();
        User mockUser = new User();
        when(userRepository.findById(userID)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserByID(userID);

        //Assert
        assertEquals(mockUser, result);
    }


    @SuppressWarnings("null")
    @Test
    void getUserByID_ShouldReturnNotFound_WhenUserNotExists(){

        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserByID(userId));
    }

    @SuppressWarnings("null")
    @Test
    @Deprecated
    void createUser_ShouldReturnUser_WhenDataIsValid(){

        User mockUser = this.createMockUser();
        String idToken = UUID.randomUUID().toString();
        String validToken = JWTUtil.generateToken(idToken);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User result = userService.createUser(mockUser, validToken);
        assertEquals(mockUser.getName(), result.getName());
        verify(userRepository, times(1)).save(mockUser);

    }


}
