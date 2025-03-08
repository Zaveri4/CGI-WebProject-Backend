package org.example.cgiproject_backend.service;


import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.mapping.SeatMapper;
import org.example.cgiproject_backend.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public List<SeatDto> suggestSeat(Long flightId, Integer numSeats, boolean preferWindow, boolean prefersLegroom, boolean isNearExit) {
        return getSeatsByFlightId(flightId).stream()
                .filter(seat -> !seat.isOccupied())
                .filter(seat -> seat.isWindowSeat() == preferWindow
                        && seat.isHasExtraLegroom() == prefersLegroom
                        && seat.isNearExit() == isNearExit)
                .limit(numSeats)
                .toList();
    }

    public List<SeatDto> getSeatsByFlightId(Long flightId) {
        return seatMapper.toDtoList(seatRepository.findSeatsByFlightId(flightId));
    }
}
