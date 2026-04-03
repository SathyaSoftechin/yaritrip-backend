package com.yaritrip.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PackageOptionDTO {
    private UUID id; 
    private String title;
    private double price;
    private String hotelType;
    private boolean flightIncluded;
}