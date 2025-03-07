package org.example.cgiproject_backend.repository;

import org.example.cgiproject_backend.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
}
