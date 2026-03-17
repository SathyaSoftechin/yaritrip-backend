package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID packageId;

    private double totalAmount;

    private String status;

    private String passport;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<TravellerDetails> travellers;
}