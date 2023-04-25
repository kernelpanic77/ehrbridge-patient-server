package com.ehrbridge.ehrbridgepatient.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ehrbridge.ehrbridgepatient.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehrbridge.ehrbridgepatient.dto.User.LoginRequest;
import com.ehrbridge.ehrbridgepatient.dto.User.LoginResponse;
import com.ehrbridge.ehrbridgepatient.dto.User.PatientByIDResponse;
import com.ehrbridge.ehrbridgepatient.dto.User.RegisterRequest;
import com.ehrbridge.ehrbridgepatient.dto.User.RegisterResponse;
import com.ehrbridge.ehrbridgepatient.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;


    public ResponseEntity<RegisterResponse> register(RegisterRequest request){
        var user = User.builder()
                       .address(request.getAddress())
                       .gender(request.getGender())
                       .ehrbID(request.getEhrbID())
                       .email(request.getEmailAddress())
                       .password(request.getPassword())
                       .firstName(request.getFirstName())
                       .lastName(request.getLastName())
                       .build();                       
        try {
            userRepository.save(user);
            return new ResponseEntity<RegisterResponse>(RegisterResponse.builder().message("Patient Registeres with Server").build(), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new ResponseEntity<RegisterResponse>(RegisterResponse.builder().message("Unable to register patient with the server").build(), HttpStatusCode.valueOf(500));
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request){
        var user = userRepository.findByEmail(request.getEmail());
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<LoginResponse>(LoginResponse.builder().message("Unable to authenticate user, Email/Password is incorrect!").build(), HttpStatusCode.valueOf(403));
        }

        var jwtToken = jwtService.generateToken(user.get());
        return new ResponseEntity<LoginResponse>(LoginResponse.builder().token(jwtToken).message("User authenticated successfully!").ehrbID(user.get().getEhrbID()).build(), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<PatientByIDResponse> getByID(String ehrbID) {
        List<User> userList = userRepository.findAll();
        User findUser = null;
        for (User user : userList) {
            if (user.getEhrbID().equals(ehrbID)) {
                findUser = user;
                break;
            }
        }
        PatientByIDResponse response = null;
        if (findUser != null) { 
            response = PatientByIDResponse.builder()
                                .firstName(findUser.getFirstName())
                                .lastName(findUser.getLastName())
                                .email(findUser.getEmail())
                                .gender(findUser.getGender())
                                .address(findUser.getAddress())
                                .phoneString(findUser.getPhoneString())
                                .ehrbID(findUser.getEhrbID())
                                .password(findUser.getPassword())
                                .build();
        }
        return new ResponseEntity<PatientByIDResponse>(response, HttpStatusCode.valueOf(200));
    }

}
