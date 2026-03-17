package com.example.demo.controller;

import com.example.demo.dto.AttractionPackageResponse;
import com.example.demo.dto.CreateAttractionPackageRequest;
import com.example.demo.model.AttractionPackage;
import com.example.demo.service.AttractionPackageService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttractionPackageController {

    private final AttractionPackageService service;



    @PostMapping("/attraction-packages")
    public AttractionPackage createPackage(@RequestBody CreateAttractionPackageRequest request) {
        return service.createPackage(request);
    }



    @GetMapping("/attraction-packages/{id}")
    public ResponseEntity<AttractionPackageResponse> getById(@PathVariable UUID id) {

        AttractionPackage pkg = service.getPackage(id);

        return ResponseEntity.ok(buildResponse(pkg));
    }



    @GetMapping("/attractions/{attractionId}/package")
    public ResponseEntity<AttractionPackageResponse> getByAttractionId(@PathVariable UUID attractionId) {

        AttractionPackage pkg = service.getPackageByAttractionId(attractionId);

        return ResponseEntity.ok(buildResponse(pkg));
    }


    private AttractionPackageResponse buildResponse(AttractionPackage pkg) {

        return AttractionPackageResponse.builder()
                .id(pkg.getId())
                .title(pkg.getTitle())
                .location(pkg.getLocation())
                .nights(pkg.getNights())
                .price(pkg.getPrice())
                .rating(pkg.getRating() != null ? pkg.getRating() : 4.5)
                .image("http://localhost:8082" + pkg.getImageUrl())
                .images(List.of("http://localhost:8082" + pkg.getImageUrl()))
                .overview(pkg.getOverview())
                .build();
    }
}