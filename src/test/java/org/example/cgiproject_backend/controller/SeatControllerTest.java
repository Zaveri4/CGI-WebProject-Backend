package org.example.cgiproject_backend.controller;

import org.example.cgiproject_backend.AbstractIntegrationTest;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.example.cgiproject_backend.entity.SeatEntity;
import org.example.cgiproject_backend.repository.FlightRepository;
import org.example.cgiproject_backend.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SeatControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    private Long flightId;

    @BeforeEach
    void setUp() {
        seatRepository.deleteAll();
        flightRepository.deleteAll();

        // Подготовка тестовых данных
        FlightEntity flight = new FlightEntity(null, "Paris", LocalDate.of(2025, 3, 15), LocalTime.of(14, 30), 200.0, 180, null);
        flight = flightRepository.save(flight);
        flightId = flight.getId();

        SeatEntity seat1 = new SeatEntity(null, flight, "1A", false, true, false, false);
        SeatEntity seat2 = new SeatEntity(null, flight, "1B", false, false, true, false);
        SeatEntity seat3 = new SeatEntity(null, flight, "2A", true, true, false, true);
        seatRepository.saveAll(java.util.List.of(seat1, seat2, seat3));
    }

    @Test
    void getSeatsByFlightId_Success() throws Exception {
        mvc.perform(get("/api/seat/" + flightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"))
                .andExpect(jsonPath("$[1].seatNumber").value("1B"))
                .andExpect(jsonPath("$[2].seatNumber").value("2A"));
    }

    @Test
    void getSeatsByFlightId_NotFound() throws Exception {
        mvc.perform(get("/api/seat/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void suggestSeat_withoutFilters_Success() throws Exception {
        mvc.perform(get("/api/seat/recommended/" + flightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"));
    }

    @Test
    void suggestSeat_withWindowPreference_Success() throws Exception {
        mvc.perform(get("/api/seat/recommended/" + flightId + "?prefersWindow=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"))
                .andExpect(jsonPath("$[0].windowSeat").value(true)); // Исправлено с isWindowSeat на windowSeat
    }

    @Test
    void suggestSeat_withNumSeats_Success() throws Exception {
        mvc.perform(get("/api/seat/recommended/" + flightId + "?numSeats=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"))
                .andExpect(jsonPath("$[1].seatNumber").value("1B"));
    }

    @Test
    void suggestSeat_withAllPreferences_Success() throws Exception {
        // Добавим подходящее место
        FlightEntity flight = flightRepository.findById(flightId).get();
        SeatEntity seat4 = new SeatEntity(null, flight, "3A", false, true, true, true);
        seatRepository.save(seat4);

        mvc.perform(get("/api/seat/recommended/" + flightId + "?prefersWindow=true&prefersLegroom=true&isNearExit=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].seatNumber").value("3A"))
                .andExpect(jsonPath("$[0].windowSeat").value(true))      // Исправлено с isWindowSeat на windowSeat
                .andExpect(jsonPath("$[0].hasExtraLegroom").value(true))
                .andExpect(jsonPath("$[0].nearExit").value(true));       // Исправлено с isNearExit на nearExit
    }
}