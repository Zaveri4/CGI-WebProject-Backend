package org.example.cgiproject_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{flightId}")
    public ResponseEntity<List<SeatDto>> suggestSeat(@PathVariable Long flightId,
                                                     @RequestParam(required = false) Integer numSeats,
                                                     @RequestParam(required = false) boolean prefersWindow,
                                                     @RequestParam(required = false) boolean prefersLegroom,
                                                     @RequestParam(required = false) boolean isNearExit) {
        if (numSeats == null) {
            numSeats = 1;
        }
        return ResponseEntity.ok(seatService.suggestSeat(flightId, numSeats, prefersWindow, prefersLegroom, isNearExit));
    }
}
