package org.example.cgiproject_backend.dto;

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
public class FlightDto {
    private Long id;

    private String destination;
    private LocalDate date;
    private LocalTime departureTime;
    private double price;
}
