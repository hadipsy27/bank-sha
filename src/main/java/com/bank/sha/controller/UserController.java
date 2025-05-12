package com.bank.sha.controller;

import com.bank.sha.entity.User;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.handler.ResponseHandler;
import com.bank.sha.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @GetMapping()
    public ResponseEntity<Object> getUsers() {
        return ResponseHandler.generateResponse("Success get all user", HttpStatus.OK, userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<Object> authenticatedUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        return ResponseHandler.generateResponse("Success get authenticated user", HttpStatus.OK, user);
    }

}
