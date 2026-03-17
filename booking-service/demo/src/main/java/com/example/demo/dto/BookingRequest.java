package com.example.demo.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingRequest {

    private UUID packageId;

    private List<TravellerRequest> travellers;

    private double totalAmount;
}