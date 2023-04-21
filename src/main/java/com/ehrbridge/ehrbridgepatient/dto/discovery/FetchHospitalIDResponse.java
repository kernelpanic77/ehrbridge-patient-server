package com.ehrbridge.ehrbridgepatient.dto.discovery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.ehrbridge.ehrbridgepatient.entity.Discovery;
/**
 * FetchHospitalIDResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FetchHospitalIDResponse {
    private List<Discovery> hospitals; 
}