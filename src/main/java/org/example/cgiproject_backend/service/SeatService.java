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

    public List<SeatDto> getSeatsByFlightId(Long flightId) {
        return seatMapper.toDtoList(seatRepository.findSeatsByFlightId(flightId));
    }
}
