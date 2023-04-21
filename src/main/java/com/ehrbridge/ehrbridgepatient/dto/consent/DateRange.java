package com.ehrbridge.ehrbridgepatient.dto.consent;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DateRange
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRange {
    public Date from;
    public Date to;    
}