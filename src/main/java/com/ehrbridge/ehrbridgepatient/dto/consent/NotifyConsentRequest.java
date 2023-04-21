package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NotifyConsentRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyConsentRequest {
    public String txnID;
    public String status;
}