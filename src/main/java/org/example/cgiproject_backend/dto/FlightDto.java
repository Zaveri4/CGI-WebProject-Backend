package org.example.cgiproject_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Flight information")
public class FlightDto {
    @Schema(description = "Flight ID", example = "1")
    private Long id;

    @Schema(description = "Destination of the flight", example = "Estonia")
    private String destination;

    @Schema(description = "Flight departure date", example = "2025-03-10")
    private LocalDate date;

    @Schema(description = "Flight departure time", example = "15:30")
    private LocalTime departureTime;

    @Schema(description = "Flight duration in minutes", example = "360")
    private int duration;

    @Schema(description = "Price for the flight", example = "185.50")
    private double price;
}
