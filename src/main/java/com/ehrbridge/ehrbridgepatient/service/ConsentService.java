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

import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentObject;
import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentPermission;
import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.ConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.DateRange;
import com.ehrbridge.ehrbridgepatient.dto.consent.FetchConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentRequest;
import com.ehrbridge.ehrbridgepatient.dto.consent.NotifyConsentResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.GetConsentReqResponse;
import com.ehrbridge.ehrbridgepatient.dto.consent.RequestDetails;
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
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<NotifyConsentResponse>(HttpStatusCode.valueOf(500));
        }
        if(consentReq.isPresent()){
            consentReq.get().setConsent_status(request.getConsent_status());
            consentReq.get().setDepartments(request.getDepartments());
            consentReq.get().setHiType(request.getHiType());
            consentReq.get().setDate_from(request.getDate_from());
            consentReq.get().setDate_to(request.getDate_to());
            consentReq.get().setConsent_validity(request.getConsent_validity());
            
            ConsentRequest consentCMObj = constructConsentJson(consentReq.get());
            try {
                String CM_CALLBACK = consentReq.get().getCallback_url();
            // send request to CM
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();   
                String jsonConsentObj = ow.writeValueAsString(consentCMObj);
                HttpEntity<String> requestEntity = new HttpEntity<String>(jsonConsentObj, headers);
                System.out.println(requestEntity.getHeaders());
                ResponseEntity<Object> responseEntity = rest.exchange(CM_CALLBACK, HttpMethod.POST, requestEntity, Object.class);
                System.out.println(responseEntity.getStatusCode());
                if(responseEntity.getStatusCode().value() == 200){
                    consentRepository.save(consentReq.get());
                    return new ResponseEntity<NotifyConsentResponse>(NotifyConsentResponse.builder().message("Sent Consent Callback to CM").build(), HttpStatusCode.valueOf(200));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
            // save the status to the database
        }
        return new ResponseEntity<NotifyConsentResponse>(NotifyConsentResponse.builder().message("Could not send update on consent callback URL").build(), HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<String> revokeConsent(String txnID){
        // update database with revoked status
        Optional<ConsentReqs> consentReq = null;
        try {
           consentReq  = consentRepository.findById(txnID);
           if(consentReq.isPresent()){
            consentReq.get().setConsent_status("REVOKED");
            consentRepository.save(consentReq.get());
           }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<String>(HttpStatusCode.valueOf(500));
        }
        // send request to gateway with revoked status
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new ResponseEntity<String>("Could not send revoke request to gateway", HttpStatusCode.valueOf(501));
    }

    private ConsentRequest constructConsentJson(ConsentReqs consent){
        DateRange range = DateRange.builder()
                                   .from(consent.getDate_from())
                                   .to(consent.getDate_to())
                                   .build();
        
        ConsentPermission consentPermission = ConsentPermission.builder()
                                                               .consent_validity(consent.getConsent_validity())
                                                               .dateRange(range)
                                                            .build();
        
        ConsentObject consentObject = ConsentObject.builder()
                                                   .hiuID(consent.getHiuID())
                                                   .hipID(consent.getHipID())
                                                   .hiType(consent.getHiType())
                                                   .departments(consent.getDepartments())
                                                   .consentID(consent.getConsentID())
                                                   .consent_status(consent.getConsent_status())
                                                   .consentDescription(consent.getConsentDescription())
                                                   .doctorID(consent.getDoctorID())
                                                   .permission(consentPermission)
                                                   .ehrbID(consent.getEhrbID())
                                                   .build();
        
        RequestDetails details = RequestDetails.builder()
                                               .doctorName(consent.getDoctorName())
                                               .hipName(consent.getHipName())
                                               .hiuName(consent.getHiuName())
                                               .build();                                           
        return ConsentRequest.builder()
                             .txnID(consent.getTxnID())
                             .consent_obj(consentObject)
                             .request_details(details)
                             .build();
    }

    public ResponseEntity<GetConsentReqResponse> getConsentReq(String txnID) {
        List<ConsentReqs> consentRequests = consentRepository.findAll();
        GetConsentReqResponse response = null;
        for (ConsentReqs consentRequest : consentRequests) {
            if(consentRequest.getTxnID().equals(txnID)) {
                response = GetConsentReqResponse.builder()
                                .txnID(consentRequest.getTxnID())
                                .hiuName(consentRequest.getHiuName())
                                .hipName(consentRequest.getHipName())
                                .doctorName(consentRequest.getDoctorName())
                                .consentID(consentRequest.getConsentID())
                                .ehrbID(consentRequest.getEhrbID())
                                .hiuID(consentRequest.getHiuID())
                                .hipID(consentRequest.getHipID())
                                .doctorID(consentRequest.getDoctorID())
                                .hiType(consentRequest.getHiType())
                                .departments(consentRequest.getDepartments())
                                .consentDescription(consentRequest.getConsentDescription())
                                .consent_status(consentRequest.getConsent_status())
                                .consent_validity(consentRequest.getConsent_validity())
                                .date_from(consentRequest.getDate_from())
                                .date_to(consentRequest.getDate_to())
                                .callback_url(consentRequest.getCallback_url())
                                .build();
                break;
            }
        }
        return new ResponseEntity<GetConsentReqResponse>(response, HttpStatusCode.valueOf(200));
    }
    
}