package com.ehrbridge.ehrbridgepatient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehrbridge.ehrbridgepatient.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
}
