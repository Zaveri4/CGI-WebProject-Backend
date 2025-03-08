package org.example.cgiproject_backend.service;


import lombok.RequiredArgsConstructor;
import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.entity.SeatEntity;
import org.example.cgiproject_backend.mapping.SeatMapper;
import org.example.cgiproject_backend.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public List<SeatDto> suggestSeat(Long flightId, int numSeats, boolean windowSeat, boolean extraLegroom, boolean isNearExit) {
        List<SeatEntity> allSeats = seatRepository.findSeatsByFlightId(flightId);
        List<SeatDto> availableSeats = allSeats.stream()
                .filter(seatEntity -> !seatEntity.isOccupied())
                .map(seatMapper::toDto)
                .toList();

        // Grupeerime istmed ridade kaupa
        Map<Integer, List<SeatDto>> seatsByRow = availableSeats.stream()
                .collect(Collectors.groupingBy(seat -> getRowNumber(seat.getSeatNumber())));

        // Otsime sobiva rea, kus on piisavalt vabu istekohti järjest
        for (List<SeatDto> rowSeats : seatsByRow.values()) {
            List<SeatDto> sortedRowSeats = rowSeats.stream()
                    .sorted(Comparator.comparing(SeatDto::getSeatNumber))
                    .toList();

            for (int i = 0; i <= sortedRowSeats.size() - numSeats; i++) {
                List<SeatDto> seatGroup = sortedRowSeats.subList(i, i + numSeats);

                if (isValidSeatGroup(seatGroup, windowSeat, extraLegroom, isNearExit)) {
                    return seatGroup;
                }
            }
        }

        // Kui ideaalset varianti ei leitud, tagastame lihtsalt esimesed järjestikused kohad
        return availableSeats.stream()
                .sorted(Comparator.comparing(SeatDto::getSeatNumber))
                .limit(numSeats)
                .toList();
    }

    private boolean isValidSeatGroup(List<SeatDto> seatGroup, boolean windowSeat, boolean extraLegroom, boolean isNearExit) {
        boolean hasWindowSeat = seatGroup.stream().anyMatch(SeatDto::isWindowSeat);
        boolean hasExtraLegroom = seatGroup.stream().anyMatch(SeatDto::isHasExtraLegroom);
        boolean isItNearExit = seatGroup.stream().anyMatch(SeatDto::isNearExit);

        // Peame tagama, et vähemalt ÜKS istekoht vastab eelistustele
        return (!windowSeat || hasWindowSeat) && (!extraLegroom || hasExtraLegroom) && (!isNearExit || isItNearExit);
    }

    private int getRowNumber(String seatNumber) {
        // Eeldame, et istekoht on kujul "12A", "15B"
        return Integer.parseInt(seatNumber.replaceAll("\\D", ""));
    }

    public List<SeatDto> getSeatsByFlightId(Long flightId) {
        return seatMapper.toDtoList(seatRepository.findSeatsByFlightId(flightId));
    }
}
