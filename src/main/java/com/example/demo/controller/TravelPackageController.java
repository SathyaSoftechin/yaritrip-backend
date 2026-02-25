package com.example.demo.controller;

import com.example.demo.model.TravelPackage;
import com.example.demo.repository.TravelPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TravelPackageController {

    private final TravelPackageRepository repository;

    @GetMapping("/{id}")
    public TravelPackage getById(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}