package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * ConsentPermission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsentPermission {
    public DateRange dateRange;

    public Date consent_validity;
}