package com.creditcalculator.controller;

import com.creditcalculator.exception.ForbiddenException;
import com.creditcalculator.payload.request.UserUpdateRequest;
import com.creditcalculator.payload.response.MessageResponse;
import com.creditcalculator.services.UserDetailsImpl;
import com.creditcalculator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        if (!userUpdateRequest.getUsername().equals(getPrincipal().getUsername()))
        {
            throw new ForbiddenException("Users can make requests only for their own accounts");
        }
        return ResponseEntity.ok(userService.updateUser(userUpdateRequest));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody String username){
        if (!username.equals(getPrincipal().getUsername()))
        {
            throw new ForbiddenException("Users can delete only their own accounts");
        }
        userService.deleteUser(username);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    private UserDetailsImpl getPrincipal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) auth.getPrincipal();
    }
}
