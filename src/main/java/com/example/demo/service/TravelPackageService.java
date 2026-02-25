package com.example.demo.service;

import com.example.demo.model.City;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.TravelPackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;
    private final CityRepository cityRepository;

    @Transactional
    public TravelPackage create(
            UUID fromCityId,
            UUID destinationCityId,
            LocalDate departureDate,
            int totalRooms,
            int guestsPerRoom,
            Double price
    ) {

        City fromCity = cityRepository.findById(fromCityId)
                .orElseThrow(() -> new RuntimeException("From city not found"));

        City destinationCity = cityRepository.findById(destinationCityId)
                .orElseThrow(() -> new RuntimeException("Destination city not found"));

        TravelPackage pkg = TravelPackage.builder()
                .fromCity(fromCity)
                .destinationCity(destinationCity)
                .departureDate(departureDate)
                .totalRooms(totalRooms)
                .guestsPerRoom(guestsPerRoom)
                .price(price)
                .build();

        return travelPackageRepository.save(pkg);
    }
}