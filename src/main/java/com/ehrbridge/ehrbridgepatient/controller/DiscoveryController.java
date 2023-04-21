package com.ehrbridge.ehrbridgepatient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ehrbridge.ehrbridgepatient.dto.discovery.FetchHospitalIDResponse;
import com.ehrbridge.ehrbridgepatient.dto.discovery.NotifyVisitRequest;
import com.ehrbridge.ehrbridgepatient.service.DiscoveryService;

import lombok.RequiredArgsConstructor;

/**
 * DiscoveryController
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discovery")
public class DiscoveryController {  

    @Autowired
    private DiscoveryService discoveryService;

    @PostMapping("/notify-visit")
    public ResponseEntity<String> notifyVisit(@RequestBody NotifyVisitRequest request){
        return discoveryService.registerVisit(request);
    }

    @GetMapping("/fetch-hospitals")
    public ResponseEntity<FetchHospitalIDResponse> fetchHospitals(@RequestParam String ehrbID){
        return discoveryService.discoverHospitals(ehrbID);
    }
}