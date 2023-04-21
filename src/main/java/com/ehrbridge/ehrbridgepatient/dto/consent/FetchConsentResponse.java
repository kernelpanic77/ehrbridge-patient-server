package com.ehrbridge.ehrbridgepatient.dto.consent;

import com.ehrbridge.ehrbridgepatient.entity.ConsentReqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchConsentResponse {
    private List<ConsentReqs> consentReqs;
}
