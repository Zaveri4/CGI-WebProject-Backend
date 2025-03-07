package org.example.cgiproject_backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.FlightDto;
import org.example.cgiproject_backend.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/flight")
@Tag(name = "Flights", description = "Flight management APIs")
public class FlightController {
    private final FlightService flightService;

    @Operation(
            summary = "Get all flights or filter by destination, date, duration, and price",
            description = "Return all available flights. If filters are provided, return only matching flights."
    )
    @ApiResponse(responseCode = "200", description = "Successfully found list of flights")
    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(flightService.getFilteredFlight(destination, departureDate, minPrice, maxPrice));
    }
}
