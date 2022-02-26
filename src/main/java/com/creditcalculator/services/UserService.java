package com.creditcalculator.services;

import com.creditcalculator.exception.UserNotFoundException;
import com.creditcalculator.models.User;
import com.creditcalculator.payload.request.UserUpdateRequest;
import com.creditcalculator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User updateUser(UserUpdateRequest userUpdateRequest){
        Optional<User> optionalUser = userRepository.findByUsername(userUpdateRequest.getUsername());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setUsername(userUpdateRequest.getUsername());
            user.setFirstNameLastName(userUpdateRequest.getFirstNameLastName());
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            user.setIncome(userUpdateRequest.getIncome());
            return userRepository.save(user);

        }else {
            throw new UserNotFoundException("User ID doesn't exist.");
        }
    }

    @Transactional
    public void deleteUser(String username){
        userRepository.deleteByUsername(username);
    }

    public User getUserByUsername(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new UserNotFoundException("User ID doesn't exist.");
        }
    }
}
