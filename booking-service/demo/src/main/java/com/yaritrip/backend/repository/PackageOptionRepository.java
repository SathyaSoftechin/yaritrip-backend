package com.yaritrip.backend.repository;

import com.yaritrip.backend.model.PackageOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PackageOptionRepository extends JpaRepository<PackageOption, UUID> {
}