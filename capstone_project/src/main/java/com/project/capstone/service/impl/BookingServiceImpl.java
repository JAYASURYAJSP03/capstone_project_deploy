package com.project.capstone.service.impl;

import com.project.capstone.entity.Booking;
import com.project.capstone.repository.BookingRepository;
import com.project.capstone.repository.UserRepository;
import com.project.capstone.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllById(Long id) {
        return userRepository.findBookingsByUserId(id).stream().sorted(Comparator
                        .comparingLong(Booking::getId)
                        .thenComparing(Booking::getDate))
                        .collect(Collectors.toList());
    }

    @Override
    public Booking getById(Long id) {
        return bookingRepository.findById(id).get();
    }
}
