package com.ehrbridge.ehrbridgepatient.dto.consent;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetConsentReqResponse {
    public String txnID;
    public String hiuName;
    public String hipName;
    public String doctorName;
    public String consentID;
    public String ehrbID;
    public String hiuID;
    
    public String hipID;
    
    public String doctorID;
    public String[] hiType;
    public String[] departments;
    public String consentDescription;
    public Date consent_validity;
    public Date date_from;
    public Date date_to;
    public String callback_url;
    public String consent_status;
}
