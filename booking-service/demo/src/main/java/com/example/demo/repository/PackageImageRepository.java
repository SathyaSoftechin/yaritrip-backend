package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.PackageImage;

import java.util.UUID;

public interface PackageImageRepository extends JpaRepository<PackageImage, UUID> {
}