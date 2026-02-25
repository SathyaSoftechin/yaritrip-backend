package com.example.demo.controller;

import com.example.demo.dto.AttractionDetailResponse;
import com.example.demo.dto.AttractionUpdateRequest;
import com.example.demo.dto.PopularAttractionResponse;
import com.example.demo.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/popular")
    public List<PopularAttractionResponse> getPopular(@RequestParam String city) {
        return attractionService.getPopularByCity(city);
    }

    @GetMapping("/{id}")
    public AttractionDetailResponse getAttraction(@PathVariable UUID id) {
        return attractionService.getAttractionById(id);
    }

    @PutMapping("/{id}")
    public AttractionDetailResponse updateAttraction(
            @PathVariable UUID id,
            @RequestBody AttractionUpdateRequest request) {
        return attractionService.updateAttraction(id, request);
    }
}