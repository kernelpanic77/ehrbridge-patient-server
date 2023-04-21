package com.ehrbridge.ehrbridgepatient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehrbridge.ehrbridgepatient.dto.User.LoginRequest;
import com.ehrbridge.ehrbridgepatient.dto.User.LoginResponse;
import com.ehrbridge.ehrbridgepatient.dto.User.RegisterRequest;
import com.ehrbridge.ehrbridgepatient.dto.User.RegisterResponse;
import com.ehrbridge.ehrbridgepatient.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> regsiterUser(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request){
        return userService.login(request);
    }

}
