package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConsentObject
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsentObject {
    public String consentID;
    public String ehrbID;
    public String hiuID;
    public String hipID;
    public String doctorID;
    public String[] hiType;
    public String[] departments;
    public String consentDescription;
    public ConsentPermission permission;
    public String consent_status;
    
}