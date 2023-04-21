package com.ehrbridge.ehrbridgepatient.dto.consent;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DateRange
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    public Date from;
    public Date to;    
}