package com.example.demo.repository;

import com.example.demo.model.TravelPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, UUID> {

    @Query("""
        SELECT DISTINCT p.destinationCity
        FROM TravelPackage p
        WHERE p.fromCity.id = :fromCityId
    """)
    List<com.example.demo.model.City> findDestinationsByFromCity(
            @Param("fromCityId") UUID fromCityId
    );

    @Query("""
        SELECT p FROM TravelPackage p
        WHERE p.fromCity.id = :fromCity
        AND p.destinationCity.id = :destination
        AND p.departureDate = :date
        AND p.totalRooms >= :rooms
        AND (p.guestsPerRoom * :rooms) >= :guests
    """)
    List<TravelPackage> searchPackages(
            @Param("fromCity") UUID fromCity,
            @Param("destination") UUID destination,
            @Param("date") LocalDate date,
            @Param("rooms") int rooms,
            @Param("guests") int guests
    );
}