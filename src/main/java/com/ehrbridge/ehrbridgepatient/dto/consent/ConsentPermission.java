package com.ehrbridge.ehrbridgepatient.dto.consent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * ConsentPermission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentPermission {
    public DateRange dateRange;

    public Date consent_validity;
}