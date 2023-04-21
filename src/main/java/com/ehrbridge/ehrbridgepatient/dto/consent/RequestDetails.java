package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RequestDetails
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetails {
    public String hiuName;
    public String hipName;
    public String doctorName;
}