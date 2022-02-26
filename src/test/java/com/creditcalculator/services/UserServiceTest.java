package com.creditcalculator.services;

import com.creditcalculator.exception.UserNotFoundException;
import com.creditcalculator.models.User;
import com.creditcalculator.payload.request.UserUpdateRequest;
import com.creditcalculator.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;
    static String USERNAME = "60602124412";


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void testUpdateUser(){
        Optional<User> optionalUser = Optional.of(getMockUser());
        when(userRepository.findByUsername(USERNAME)).thenReturn(optionalUser);
        User expectedUser =new User("Bobby", USERNAME, "235 211 24 40", 2100.00, "12345");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userService.updateUser(new UserUpdateRequest(expectedUser.getUsername(),
                expectedUser.getFirstNameLastName(),
                expectedUser.getPhoneNumber(),
                expectedUser.getIncome()));
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void testNonExistentUser(){
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findByUsername(USERNAME)).thenReturn(optionalUser);
        Assertions.assertThrows(UserNotFoundException.class,()-> userService.updateUser(new UserUpdateRequest(USERNAME,"Jared","555242 421 21 21", 6000.00)));

    }

    @Test
    public void testGetUserByUsername(){
        User mockUser = getMockUser();
        Optional<User> optionalUser = Optional.of(mockUser);
        when(userRepository.findByUsername(USERNAME)).thenReturn(optionalUser);
        User actualUser = userService.getUserByUsername(USERNAME);
        assertThat(actualUser).isEqualTo(mockUser);
    }

    @Test
    public void testGetNonExistingUserByUsername(){
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findByUsername(USERNAME)).thenReturn(optionalUser);
        Assertions.assertThrows(UserNotFoundException.class,()-> userService.getUserByUsername(USERNAME));
    }


    private User getMockUser() {
        return new User("Test Name", USERNAME, "235 211 24 24", 4100.00, "12345");
    }

    //String firstNameLastName, String username, String phoneNumber, Double income, String password


}