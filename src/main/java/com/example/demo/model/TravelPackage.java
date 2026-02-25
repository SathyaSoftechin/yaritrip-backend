package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
    indexes = {
        @Index(name = "idx_from_city", columnList = "from_city_id"),
        @Index(name = "idx_destination_city", columnList = "destination_city_id"),
        @Index(name = "idx_departure_date", columnList = "departureDate")
    }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_city_id", nullable = false)
    private City fromCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private int totalRooms;

    @Column(nullable = false)
    private int guestsPerRoom;

    @Column(nullable = false)
    private Double price;
}