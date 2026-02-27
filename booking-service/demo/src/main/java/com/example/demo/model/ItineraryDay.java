package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import com.example.demo.model.TravelPackage;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryDay {
@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int dayNumber;

    @Column(length = 3000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private TravelPackage travelPackage;
}
