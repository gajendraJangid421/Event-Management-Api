package com.example.event_management.repository;

import com.example.event_management.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {

    void deleteByToken(String token);

    @Query("SELECT s FROM Session s WHERE s.token = :requestToken")
    Session findByToken(@Param("requestToken") String requestToken);
    
}
