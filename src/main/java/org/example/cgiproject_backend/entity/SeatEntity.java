package org.example.cgiproject_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "is_window_seat")
    private boolean isWindowSeat;

    @Column(name = "has_extra_legroom")
    private boolean hasExtraLegroom;

    @Column(name = "is_near_exit")
    private boolean isNearExit;

}

