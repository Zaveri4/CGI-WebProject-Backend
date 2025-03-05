package org.example.cgiproject_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.FlightDto;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.example.cgiproject_backend.mapping.FlightMapper;
import org.example.cgiproject_backend.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public List<FlightDto> getFilteredFlight(String destination, String departureDate, Integer minDuration, Integer maxDuration, Double minPrice, Double maxPrice) {
        List<FlightEntity> flightEntities = flightRepository.findAll();

        return flightEntities.stream()
                .filter(flight -> destination == null || flight.getDestination().equalsIgnoreCase(destination))
                .filter(flight -> departureDate == null || flight.getDate().toString().equals(departureDate))
                .filter(flight -> minDuration == null || flight.getDuration() >= minDuration)
                .filter(flight -> maxDuration == null || flight.getDuration() <= maxDuration)
                .filter(flight -> minPrice == null || flight.getPrice() >= minPrice)
                .filter(flight -> maxPrice == null || flight.getPrice() <= maxPrice)
                .map(flightMapper::toDto)
                .toList();
    }
}
