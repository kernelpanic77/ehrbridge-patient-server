package com.ehrbridge.ehrbridgepatient.service;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ehrbridge.ehrbridgepatient.dto.discovery.FetchHospitalIDResponse;
import com.ehrbridge.ehrbridgepatient.dto.discovery.NotifyVisitRequest;
import com.ehrbridge.ehrbridgepatient.entity.Discovery;
import com.ehrbridge.ehrbridgepatient.repository.DiscoveryRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
/**
 * DiscoveryService
 */
@Service
@RequiredArgsConstructor
public class DiscoveryService {
    
    private final DiscoveryRepository discoveryRepository;

    public ResponseEntity<String> registerVisit(NotifyVisitRequest request){
        var visit = Discovery.builder()
                            .department(request.getDepartment())
                            .ehrbID(request.getEhrbID())
                            .hospitalID(request.getHospitalID())
                            .hospitalName(request.getHospitalName())
                            .build();
        try {
            discoveryRepository.save(visit);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<String>(HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<String>("Registered Visit successfully!", HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<FetchHospitalIDResponse> discoverHospitals(String ehrbID){
        Optional<List<Discovery>> hospitals;
        try {
            hospitals = discoveryRepository.findAllByEhrbID(ehrbID);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<FetchHospitalIDResponse>(HttpStatusCode.valueOf(500));
        }

        return new ResponseEntity<FetchHospitalIDResponse>(FetchHospitalIDResponse.builder().hospitals(hospitals.get()).build(), HttpStatusCode.valueOf(200));
    }   
}