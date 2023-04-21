package com.ehrbridge.ehrbridgepatient.entity;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConsentReqs
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "consent_requests")
@NoArgsConstructor
@Builder
public class ConsentReqs {
    @Id
    public String txnID;
    public String hiuName;
    public String hipName;
    public String doctorName;
    public String consentID;
    public String ehrbID;
    public String hiuID;
    
    public String hipID;
    
    public String doctorID;
    public String[] hiType;
    public String[] departments;
    public String consentDescription;
    public Date consent_validity;
    public Date date_from;
    public Date date_to;
    public String callback_url;
    public String consent_status;
}