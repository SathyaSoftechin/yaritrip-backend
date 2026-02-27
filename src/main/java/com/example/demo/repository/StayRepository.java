package com.example.demo.repository;

import com.example.demo.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StayRepository extends JpaRepository<Stay, UUID> {

    List<Stay> findByRegion(String region);

    List<Stay> findByRegionAndIsPremium(String region, Boolean isPremium);
}