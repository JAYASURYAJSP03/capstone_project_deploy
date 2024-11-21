package com.project.capstone.service;


import com.project.capstone.entity.Booking;

import java.util.List;

public interface BookingService {
    void save(Booking booking);

    List<Booking> getAllById(Long id);

    Booking getById(Long id);
}
