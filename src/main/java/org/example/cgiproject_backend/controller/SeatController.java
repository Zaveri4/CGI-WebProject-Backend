package org.example.cgiproject_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/seat")
@Tag(name = "Seats", description = "Seat management APIs")
public class SeatController {
    private final SeatService seatService;

    @Operation(summary = "Get seats by ID", description = "Return a list of seats for given flight.")
    @ApiResponse(responseCode = "200", description = "Successfully founded seats")
    @ApiResponse(responseCode = "400", description = "Invalid flight ID")
    @GetMapping
    public ResponseEntity<List<SeatDto>> getSeatsByFlightId(@RequestParam Long flightId) {
        return ResponseEntity.ok(seatService.getSeatsByFlightId(flightId));
    }
}
