package com.ehrbridge.ehrbridgepatient.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String gender; 
    private String address;
    private String phoneString;
    private String password;
    private String ehrbID;
}
