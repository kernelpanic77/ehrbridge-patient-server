package com.ehrbridge.ehrbridgepatient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ehrbridge.ehrbridgepatient.entity.ConsentReqs;

/**
 * ConsentRepository
 */
public interface ConsentRepository extends JpaRepository<ConsentReqs, String> {
    @Query(value = "select * from consent_requests where ehrbID = ?1", nativeQuery = true)
    List<ConsentReqs> findAllByEhrbID(String ehrbID);
}