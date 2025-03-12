package org.example.cgiproject_backend.controller;

import org.example.cgiproject_backend.AbstractIntegrationTest;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.example.cgiproject_backend.repository.FlightRepository;
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
class FlightControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();
        // Подготовка тестовых данных
        FlightEntity flight1 = new FlightEntity(null, "Paris", LocalDate.of(2025, 3, 15), LocalTime.of(14, 30), 200.0, 180, null);
        FlightEntity flight2 = new FlightEntity(null, "London", LocalDate.of(2025, 3, 16), LocalTime.of(9, 0), 150.0, 120, null);
        flightRepository.saveAll(java.util.List.of(flight1, flight2));
    }

    @Test
    void getFlights_withoutFilters_Success() throws Exception {
        mvc.perform(get("/api/flight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].destination").value("Paris"))
                .andExpect(jsonPath("$[1].destination").value("London"));
    }

    @Test
    void getFlights_withDestinationFilter_Success() throws Exception {
        mvc.perform(get("/api/flight?destination=Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].destination").value("Paris"))
                .andExpect(jsonPath("$[0].price").value(200.0));
    }

    @Test
    void getFlights_withPriceRange_Success() throws Exception {
        mvc.perform(get("/api/flight?minPrice=100.0&maxPrice=160.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].destination").value("London"))
                .andExpect(jsonPath("$[0].price").value(150.0));
    }

    @Test
    void getFlights_withDateFilter_Success() throws Exception {
        mvc.perform(get("/api/flight?departureDate=2025-03-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].destination").value("Paris"))
                .andExpect(jsonPath("$[0].date").value("2025-03-15"));
    }
}