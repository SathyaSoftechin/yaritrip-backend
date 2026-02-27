package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PriceRequest {
    private List<UUID> activityIds;
}