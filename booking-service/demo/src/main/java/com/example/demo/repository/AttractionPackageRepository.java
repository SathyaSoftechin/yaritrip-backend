package com.example.demo.repository;

import com.example.demo.model.AttractionPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AttractionPackageRepository extends JpaRepository<AttractionPackage, UUID> {

    @Query("""
        SELECT ap.id
        FROM AttractionPackage ap
        JOIN ap.attractions a
        WHERE a.id = :attractionId
    """)
    UUID findPackageIdByAttraction(UUID attractionId);

    @Query("""
        SELECT ap
        FROM AttractionPackage ap
        JOIN ap.attractions a
        WHERE a.id = :attractionId
    """)
    Optional<AttractionPackage> findByAttractionId(UUID attractionId);
}