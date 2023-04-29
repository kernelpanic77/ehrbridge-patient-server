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

import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.FetchConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.GetConsentReqResponse;
import com.ehrbridge.ehrbridgepatient.entity.ConsentReqs;
import com.ehrbridge.ehrbridgepatient.repository.ConsentRepository;
import com.ehrbridge.ehrbridgepatient.service.ConsentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consent")
public class ConsentController {
    
    @Autowired
    private ConsentService consentService;

    @PostMapping("/recieve")
    public ResponseEntity<ConsentResponse> receiveConsentRequest(@RequestBody ConsentRequest request){
       return consentService.receiveConsentReq(request);
    }

    @GetMapping("/fetch")
    public ResponseEntity<FetchConsentResponse> fetchAllConsentRequest(@RequestParam String ehrbID){
        return consentService.fetchConsentReqs(ehrbID);
    }

    @PostMapping("/notify")
    public ResponseEntity<NotifyConsentResponse> notifyConsentStatus(@RequestBody NotifyConsentRequest request){
        return consentService.notifyConsentStatus(request);
    }       

    @PostMapping("/revoke")
    public ResponseEntity<String> revokeConsent(@RequestParam String txnID){
        return consentService.revokeConsent(txnID);
    }

    @GetMapping("get-by-txn")
    public ResponseEntity<GetConsentReqResponse> getConsentReqByTxn(@RequestParam String txnID) {
        return consentService.getConsentReq(txnID);
    }

}
