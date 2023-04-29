package com.ehrbridge.ehrbridgepatient.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientByIDResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String gender; 
    private String address;
    private String phoneString;
    private String ehrbID;
    private String password;
}
