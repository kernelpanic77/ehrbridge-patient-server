package com.ehrbridge.ehrbridgepatient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ehrbridge.ehrbridgepatient.entity.Discovery;
import java.util.List;
/**
 * DiscoveryRepository
 */
public interface DiscoveryRepository extends JpaRepository<Discovery, String>{

    @Query(value = "select * from discovery where ehrbID = ?1", nativeQuery = true)
    Optional<List<Discovery>> findAllByEhrbID(String ehrbID);
}