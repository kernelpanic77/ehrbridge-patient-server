package com.ehrbridge.ehrbridgepatient.entity;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Discovery
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discovery")
@Builder
public class Discovery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String visitID;

    private String ehrbID;

    private String hospitalID; 

    private String hospitalName;

    private Date timestamp;

    private String department;

}
