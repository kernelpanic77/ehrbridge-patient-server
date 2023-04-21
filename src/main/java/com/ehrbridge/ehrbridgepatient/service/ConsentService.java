package com.ehrbridge.ehrbridgepatient.service;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.FetchConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentResponse;
import com.ehrbridge.ehrbridgepatient.entity.ConsentReqs;
import com.ehrbridge.ehrbridgepatient.repository.ConsentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.persistence.criteria.Fetch;

import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * ConsentService
 */
@Service
@RequiredArgsConstructor
public class ConsentService {
    private final ConsentRepository consentRepository;

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private RestTemplate rest;

    public ResponseEntity<ConsentResponse> receiveConsentReq(ConsentRequest request){
        var consent = ConsentReqs.builder()
                                 .txnID(request.getTxnID())
                                 .hipName(request.getRequest_details().getHipName())
                                 .hiuName(request.getRequest_details().getHiuName())
                                 .hiuID(request.getConsent_obj().getHiuID())
                                 .hipID(request.getConsent_obj().getHipID())
                                 .doctorName(request.getRequest_details().getDoctorName())
                                 .consentID(request.getConsent_obj().getConsentID())
                                 .ehrbID(request.getConsent_obj().getEhrbID())
                                 .doctorID(request.getConsent_obj().getDoctorID())
                                 .hiType(request.getConsent_obj().getHiType())
                                 .departments(request.getConsent_obj().getDepartments())
                                 .consentDescription(request.getConsent_obj().getConsentDescription())
                                 .consent_status("PENDING")
                                 .consent_validity(request.getConsent_obj().getPermission().getConsent_validity())
                                 .date_from(request.getConsent_obj().getPermission().getDateRange().getFrom())
                                 .date_to(request.getConsent_obj().getPermission().getDateRange().getTo())
                                 .callback_url(request.getCallback_url())
                                 .build();
        
        try {
            consentRepository.save(consent);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<ConsentResponse>(ConsentResponse.builder().message("Unable to store consent request").build(), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<ConsentResponse>(ConsentResponse.builder().message("Stored Consent Request from CM").build(), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<FetchConsentResponse> fetchConsentReqs(String ehrbID){
        List<ConsentReqs> consentReqs;
        try {
            consentReqs = consentRepository.findAllByEhrbID(ehrbID);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<FetchConsentResponse>(HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<FetchConsentResponse>(FetchConsentResponse.builder().consentReqs(consentReqs).build(), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<NotifyConsentResponse> notifyConsentStatus(NotifyConsentRequest request){
        Optional<ConsentReqs> consentReq = null;
        try {
           consentReq  = consentRepository.findById(request.getTxnID());
           System.out.print(consentReq);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<NotifyConsentResponse>(HttpStatusCode.valueOf(500));
        }
        if(consentReq.isPresent()){
            consentReq.get().setConsent_status("GRANTED");
            try {
                String CM_CALLBACK = consentReq.get().getCallback_url();
            // send request to CM
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();   
                String jsonConsentObj = ow.writeValueAsString(consentReq.get());
                HttpEntity<String> requestEntity = new HttpEntity<String>(jsonConsentObj, headers);
                System.out.println(requestEntity.getHeaders());
                ResponseEntity<Object> responseEntity = rest.exchange(CM_CALLBACK, HttpMethod.POST, requestEntity, Object.class);
                System.out.println(responseEntity.getStatusCode());
                if(responseEntity.getStatusCode().value() != 200){
                    return new ResponseEntity<NotifyConsentResponse>(NotifyConsentResponse.builder().message("Could not send consent callback to CM").build(), HttpStatusCode.valueOf(501));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
            // save the status to the database
            consentRepository.save(consentReq.get());
        }
        return new ResponseEntity<NotifyConsentResponse>(NotifyConsentResponse.builder().message("Could not find consent callback URL in DB").build(), HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<String> revokeConsent(String txnID){
        // update database with revoked status
        // send request to gateway with revoked status
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new ResponseEntity<String>("Could not send revoke request to gateway", HttpStatusCode.valueOf(501));
    }
    
}