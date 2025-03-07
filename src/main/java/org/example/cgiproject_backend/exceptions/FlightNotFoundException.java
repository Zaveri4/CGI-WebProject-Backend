package org.example.cgiproject_backend.exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long flightId) {
        super(String.format("Flight not found with ID: %d", flightId));
    }
}