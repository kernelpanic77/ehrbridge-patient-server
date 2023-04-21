package com.ehrbridge.ehrbridgepatient.dto.discovery;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NotifyVisitRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyVisitRequest {
    public String ehrbID;
    public String hospitalID;
    public String hospitalName;
    public String department;
    public Date timestamp;
}