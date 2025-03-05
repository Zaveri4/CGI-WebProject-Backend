package org.example.cgiproject_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Seat information")
public class SeatDto {
    @Schema(description = "Seat number", example = "1A")
    private String seatNumber;

    @Schema(description = "Seat occupation status", example = "false")
    private boolean isOccupied;

    @Schema(description = "Seat is placed near to the window", example = "true")
    private boolean isWindowSeat;

    @Schema(description = "Seat has extra leg room", example = "false")
    private boolean hasExtraLegroom;

    @Schema(description = "Seat is near to the exit", example = "true")
    private boolean isNearExit;
}
