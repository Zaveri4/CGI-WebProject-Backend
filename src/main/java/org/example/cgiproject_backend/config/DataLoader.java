package org.example.cgiproject_backend.config;

import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.example.cgiproject_backend.entity.SeatEntity;
import org.example.cgiproject_backend.repository.FlightRepository;
import org.example.cgiproject_backend.repository.SeatRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final Random random = new Random();

    private static final List<String> DESTINATIONS = List.of(
            "Los Angeles", "Singapore", "Amsterdam", "Hong Kong",
            "Madrid", "Rome", "Bangkok", "Chicago"
    );

    @PostConstruct
    public void loadData() {
        if (flightRepository.count() == 0) {
            generateFlights(); // Generate 10 flights
        }
    }

    private void generateFlights() {
        for (int i = 0; i < 10; i++) {
            FlightEntity flight = createRandomFlight();
            flightRepository.save(flight);
            generateSeatsForFlight(flight, 18, new char[]{'A', 'B', 'C', 'D', 'E', 'F'});
        }
    }

    private FlightEntity createRandomFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setDestination(randomDestination());
        flight.setDate(randomDepartureDate());
        flight.setDepartureTime(randomDepartureTime());
        flight.setPrice(randomPrice());
        flight.setDuration(randomDuration());
        return flight;
    }

    private void generateSeatsForFlight(FlightEntity flight, int rows, char[] seatColumns) {
        List<SeatEntity> seats = new ArrayList<>();

        for (int row = 1; row <= rows; row++) {
            for (char col : seatColumns) {
                seats.add(createSeat(flight, row, col));
            }
        }
        seatRepository.saveAll(seats);
    }

    private SeatEntity createSeat(FlightEntity flight, int row, char col) {
        SeatEntity seat = new SeatEntity();
        seat.setFlight(flight);
        seat.setSeatNumber(row + String.valueOf(col));
        seat.setOccupied(random.nextDouble() < 0.3);
        seat.setWindowSeat(random.nextDouble() < 0.3);
        seat.setHasExtraLegroom(random.nextDouble() < 0.3);
        seat.setNearExit(random.nextDouble() < 0.3);
        return seat;
    }

    private String randomDestination() {
        return DESTINATIONS.get(random.nextInt(DESTINATIONS.size()));
    }

    private LocalDate randomDepartureDate() {
        return LocalDate.now().plusDays(random.nextInt(30) + 1L);
    }

    private LocalTime randomDepartureTime() {
        return LocalTime.of(random.nextInt(24), 0, 0);
    }

    private double randomPrice() {
        return 100 + random.nextInt(401);
    }

    private int randomDuration() {
        return 60 + random.nextInt(181);
    }
}
