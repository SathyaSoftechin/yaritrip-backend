package com.yaritrip.backend.service;

import com.yaritrip.backend.dto.PriceResponse;
import com.yaritrip.backend.model.Activity;
import com.yaritrip.backend.model.City;
import com.yaritrip.backend.model.TravelPackage;
import com.yaritrip.backend.repository.ActivityRepository;
import com.yaritrip.backend.repository.CityRepository;
import com.yaritrip.backend.repository.TravelPackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yaritrip.backend.model.PackageOption;
import com.yaritrip.backend.repository.PackageOptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelPackageService {

        private final TravelPackageRepository travelPackageRepository;
        private final CityRepository cityRepository;
        private final ActivityRepository activityRepository;
        private final PackageOptionRepository packageOptionRepository;

        public TravelPackage create(
                        UUID fromCityId,
                        UUID destinationCityId,
                        LocalDate departureDate,
                        int totalRooms,
                        int guestsPerRoom) {

                City fromCity = cityRepository.findById(fromCityId)
                                .orElseThrow(() -> new RuntimeException("From city not found"));

                City destinationCity = cityRepository.findById(destinationCityId)
                                .orElseThrow(() -> new RuntimeException("Destination city not found"));

                TravelPackage pkg = TravelPackage.builder()
                                .fromCity(fromCity)
                                .toCity(destinationCity)
                                .departureDate(departureDate)
                                .totalRooms(totalRooms)
                                .guestsPerRoom(guestsPerRoom)
                                .build();

                return travelPackageRepository.save(pkg);
        }

        @Transactional
        public PriceResponse calculatePrice(UUID optionId, List<UUID> activityIds) {

                PackageOption option = packageOptionRepository.findById(optionId)
                                .orElseThrow(() -> new RuntimeException("Option not found"));

                double basePrice = option.getPrice();
                double activitiesTotal = 0.0;

                if (activityIds != null && !activityIds.isEmpty()) {
                        List<Activity> activities = activityRepository.findAllById(activityIds);

                        activitiesTotal = activities.stream()
                                        .mapToDouble(Activity::getPrice)
                                        .sum();
                }

                double finalPrice = basePrice + activitiesTotal;

                return new PriceResponse(basePrice, activitiesTotal, finalPrice);
        }

        @Transactional
        public TravelPackage getPackageWithOptions(UUID id) {
                return travelPackageRepository.findByIdWithOptions(id)
                                .orElseThrow(() -> new RuntimeException("Package not found"));
        }

        @Transactional
        public List<TravelPackage> searchPackages(
                        String fromCode,
                        String toCode,
                        LocalDate selectedDate,
                        int rooms,
                        int guests) {

                City fromCity = cityRepository.findByCode(fromCode)
                                .orElseThrow(() -> new RuntimeException("From city not found"));

                City toCity = cityRepository.findByCode(toCode)
                                .orElseThrow(() -> new RuntimeException("Destination city not found"));

                return travelPackageRepository.searchPackages(
                                fromCity.getId(),
                                toCity.getId(),
                                selectedDate,
                                rooms,
                                guests);
        }
}