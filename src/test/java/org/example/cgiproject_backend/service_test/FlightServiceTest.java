package org.example.cgiproject_backend.service_test;

import org.example.cgiproject_backend.dto.FlightDto;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.example.cgiproject_backend.mapping.FlightMapper;
import org.example.cgiproject_backend.repository.FlightRepository;
import org.example.cgiproject_backend.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredFlight_noFilters_returnsAllFlights() {
        FlightEntity flight1 = new FlightEntity(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 185.50, 360, null);
        FlightEntity flight2 = new FlightEntity(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 150.0, 300, null);
        List<FlightEntity> flightEntities = Arrays.asList(flight1, flight2);

        FlightDto flightDto1 = new FlightDto(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 360, 185.50);
        FlightDto flightDto2 = new FlightDto(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 300, 150.0);

        when(flightRepository.findAll()).thenReturn(flightEntities);
        when(flightMapper.toDto(flight1)).thenReturn(flightDto1);
        when(flightMapper.toDto(flight2)).thenReturn(flightDto2);

        List<FlightDto> result = flightService.getFilteredFlight(null, null, null, null);

        assertEquals(2, result.size());
        assertEquals("Estonia", result.get(0).getDestination());
        assertEquals("Latvia", result.get(1).getDestination());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetFilteredFlight_withDestinationFilter_returnsFilteredFlights() {
        FlightEntity flight1 = new FlightEntity(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 185.50, 360, null);
        FlightEntity flight2 = new FlightEntity(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 150.0, 300, null);
        List<FlightEntity> flightEntities = Arrays.asList(flight1, flight2);

        FlightDto flightDto1 = new FlightDto(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 360, 185.50);

        when(flightRepository.findAll()).thenReturn(flightEntities);
        when(flightMapper.toDto(flight1)).thenReturn(flightDto1);

        List<FlightDto> result = flightService.getFilteredFlight("Estonia", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Estonia", result.getFirst().getDestination());
        verify(flightRepository, times(1)).findAll();
        verify(flightMapper, times(1)).toDto(flight1);
        verify(flightMapper, never()).toDto(flight2);
    }

    @Test
    void testGetFilteredFlight_withPriceFilter_returnsFilteredFlights() {
        FlightEntity flight1 = new FlightEntity(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 185.50, 360, null);
        FlightEntity flight2 = new FlightEntity(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 150.0, 300, null);
        List<FlightEntity> flightEntities = Arrays.asList(flight1, flight2);

        FlightDto flightDto2 = new FlightDto(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 300, 150.0);

        when(flightRepository.findAll()).thenReturn(flightEntities);
        when(flightMapper.toDto(flight2)).thenReturn(flightDto2);

        List<FlightDto> result = flightService.getFilteredFlight(null, null, 100.0, 160.0);

        assertEquals(1, result.size());
        assertEquals("Latvia", result.getFirst().getDestination());
        assertEquals(150.0, result.getFirst().getPrice());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetFilteredFlight_withDateFilter_returnsFilteredFlights() {
        FlightEntity flight1 = new FlightEntity(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 185.50, 360, null);
        FlightEntity flight2 = new FlightEntity(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 150.0, 300, null);
        List<FlightEntity> flightEntities = Arrays.asList(flight1, flight2);

        FlightDto flightDto1 = new FlightDto(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 360, 185.50);

        when(flightRepository.findAll()).thenReturn(flightEntities);
        when(flightMapper.toDto(flight1)).thenReturn(flightDto1);

        List<FlightDto> result = flightService.getFilteredFlight(null, "2025-03-10", null, null);

        assertEquals(1, result.size());
        assertEquals("Estonia", result.getFirst().getDestination());
        assertEquals(LocalDate.of(2025, 3, 10), result.getFirst().getDate());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetFilteredFlight_noMatches_returnsEmptyList() {
        FlightEntity flight1 = new FlightEntity(1L, "Estonia", LocalDate.of(2025, 3, 10), LocalTime.of(15, 30), 185.50, 360, null);
        FlightEntity flight2 = new FlightEntity(2L, "Latvia", LocalDate.of(2025, 3, 11), LocalTime.of(10, 0), 150.0, 300, null);
        List<FlightEntity> flightEntities = Arrays.asList(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flightEntities);

        List<FlightDto> result = flightService.getFilteredFlight("Germany", null, null, null);

        assertTrue(result.isEmpty());
        verify(flightRepository, times(1)).findAll();
        verify(flightMapper, never()).toDto(any(FlightEntity.class));
    }
}