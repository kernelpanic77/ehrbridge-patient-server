package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NotifyConsentResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotifyConsentResponse {
    public String message;
}