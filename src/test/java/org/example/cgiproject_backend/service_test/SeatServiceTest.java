package org.example.cgiproject_backend.service_test;

import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.entity.SeatEntity;
import org.example.cgiproject_backend.mapping.SeatMapper;
import org.example.cgiproject_backend.repository.SeatRepository;
import org.example.cgiproject_backend.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuggestSeat_noPreferences_returnsFirstAvailableSeats() {
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatNumber("1A");
        seat1.setOccupied(false);
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatNumber("1B");
        seat2.setOccupied(false);
        List<SeatEntity> seats = Arrays.asList(seat1, seat2);

        SeatDto seatDto1 = new SeatDto("1A", false, false, false, false);
        SeatDto seatDto2 = new SeatDto("1B", false, false, false, false);

        when(seatRepository.findSeatsByFlightId(1L)).thenReturn(seats);
        when(seatMapper.toDto(seat1)).thenReturn(seatDto1);
        when(seatMapper.toDto(seat2)).thenReturn(seatDto2);

        List<SeatDto> result = seatService.suggestSeat(1L, 2, false, false, false);

        assertEquals(2, result.size());
        assertEquals("1A", result.get(0).getSeatNumber());
        assertEquals("1B", result.get(1).getSeatNumber());
        verify(seatRepository, times(1)).findSeatsByFlightId(1L);
    }

    @Test
    void testSuggestSeat_withWindowPreference_returnsWindowSeats() {
        // Arrange
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatNumber("1A");
        seat1.setOccupied(false);
        seat1.setWindowSeat(true);
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatNumber("1B");
        seat2.setOccupied(false);
        List<SeatEntity> seats = Arrays.asList(seat1, seat2);

        SeatDto seatDto1 = new SeatDto("1A", false, true, false, false);
        SeatDto seatDto2 = new SeatDto("1B", false, false, false, false); // Добавляем маппинг для seat2

        when(seatRepository.findSeatsByFlightId(1L)).thenReturn(seats);
        when(seatMapper.toDto(seat1)).thenReturn(seatDto1);
        when(seatMapper.toDto(seat2)).thenReturn(seatDto2); // Исправление: мокаем seat2

        // Act
        List<SeatDto> result = seatService.suggestSeat(1L, 1, true, false, false);

        // Assert
        assertEquals(1, result.size());
        assertEquals("1A", result.get(0).getSeatNumber());
        assertTrue(result.get(0).isWindowSeat());
        verify(seatRepository, times(1)).findSeatsByFlightId(1L);
    }

    @Test
    void testSuggestSeat_withLegroomPreference_returnsLegroomSeats() {
        // Arrange
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatNumber("2A");
        seat1.setOccupied(false);
        seat1.setHasExtraLegroom(true);
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatNumber("2B");
        seat2.setOccupied(false);
        List<SeatEntity> seats = Arrays.asList(seat1, seat2);

        SeatDto seatDto1 = new SeatDto("2A", false, false, true, false);
        SeatDto seatDto2 = new SeatDto("2B", false, false, false, false); // Добавляем маппинг для seat2

        when(seatRepository.findSeatsByFlightId(1L)).thenReturn(seats);
        when(seatMapper.toDto(seat1)).thenReturn(seatDto1);
        when(seatMapper.toDto(seat2)).thenReturn(seatDto2); // Исправление: мокаем seat2

        // Act
        List<SeatDto> result = seatService.suggestSeat(1L, 1, false, true, false);

        // Assert
        assertEquals(1, result.size());
        assertEquals("2A", result.get(0).getSeatNumber());
        assertTrue(result.get(0).isHasExtraLegroom());
        verify(seatRepository, times(1)).findSeatsByFlightId(1L);
    }

    @Test
    void testSuggestSeat_noMatchingPreferences_returnsFirstAvailable() {
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatNumber("1A");
        seat1.setOccupied(false);
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatNumber("1B");
        seat2.setOccupied(false);
        List<SeatEntity> seats = Arrays.asList(seat1, seat2);

        SeatDto seatDto1 = new SeatDto("1A", false, false, false, false);
        SeatDto seatDto2 = new SeatDto("1B", false, false, false, false);

        when(seatRepository.findSeatsByFlightId(1L)).thenReturn(seats);
        when(seatMapper.toDto(seat1)).thenReturn(seatDto1);
        when(seatMapper.toDto(seat2)).thenReturn(seatDto2);

        List<SeatDto> result = seatService.suggestSeat(1L, 2, true, true, true);

        assertEquals(2, result.size());
        assertEquals("1A", result.get(0).getSeatNumber());
        assertEquals("1B", result.get(1).getSeatNumber());
        verify(seatRepository, times(1)).findSeatsByFlightId(1L);
    }

    @Test
    void testGetSeatsByFlightId_returnsAllSeats() {
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatNumber("1A");
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatNumber("1B");
        List<SeatEntity> seats = Arrays.asList(seat1, seat2);

        SeatDto seatDto1 = new SeatDto("1A", false, false, false, false);
        SeatDto seatDto2 = new SeatDto("1B", false, false, false, false);
        List<SeatDto> seatDtos = Arrays.asList(seatDto1, seatDto2);

        when(seatRepository.findSeatsByFlightId(1L)).thenReturn(seats);
        when(seatMapper.toDtoList(seats)).thenReturn(seatDtos);

        List<SeatDto> result = seatService.getSeatsByFlightId(1L);

        assertEquals(2, result.size());
        assertEquals("1A", result.get(0).getSeatNumber());
        assertEquals("1B", result.get(1).getSeatNumber());
        verify(seatRepository, times(1)).findSeatsByFlightId(1L);
    }
}