package com.example.demo.service;

import com.example.demo.dto.BookingRequest;
import com.example.demo.dto.TravellerRequest;
import com.example.demo.model.Booking;
import com.example.demo.model.TravellerDetails;
import com.example.demo.repository.BookingRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(BookingRequest request) {

        Booking booking = Booking.builder()
                .packageId(request.getPackageId())
                .totalAmount(request.getTotalAmount())
                .status("CREATED")
                .build();

        List<TravellerDetails> travellers = request.getTravellers()
                .stream()
                .map(t -> TravellerDetails.builder()
                        .name(t.getName())
                        .email(t.getEmail())
                        .mobile(t.getMobile())
                        .age(t.getAge())
                        .gender(t.getGender())
                        .passport(t.getPassport())
                        .type(t.getType())
                        .booking(booking)
                        .build()
                )
                .toList();

        booking.setTravellers(travellers);

        return bookingRepository.save(booking);
    }
}