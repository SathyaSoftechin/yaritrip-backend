package com.example.demo.controller;

import com.example.demo.dto.BookingRequest;
import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService service;

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        return service.createBooking(request);
    }
}