package org.example.cgiproject_backend.repository;

import org.example.cgiproject_backend.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findSeatsByFlightId(Long flightId);
}
