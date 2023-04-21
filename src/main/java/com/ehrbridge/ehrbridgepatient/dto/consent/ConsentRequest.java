package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentRequest {
    public String txnID;
    public RequestDetails request_details;
    public ConsentObject consent_obj;
    public String callback_url;
}
