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
    private static final List<Integer> ROWS_IS_NEAR_EXIT = List.of(1, 2, 8, 9, 17, 18);

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
            generateSeatsForFlight(flight, new char[]{'A', 'B', 'C', 'D', 'E', 'F'});
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

    private void generateSeatsForFlight(FlightEntity flight, char[] seatColumns) {
        List<SeatEntity> seats = new ArrayList<>();

        for (int row = 1; row <= 18; row++) {
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
        seat.setWindowSeat(col == 'A' || col == 'F');
        seat.setHasExtraLegroom(row == 1 || row == 9);
        seat.setNearExit(ROWS_IS_NEAR_EXIT.contains(row));
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
        return (double) 100 + random.nextInt(401);
    }

    private int randomDuration() {
        return 60 + random.nextInt(181);
    }
}
