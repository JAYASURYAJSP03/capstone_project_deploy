package com.project.capstone.controller;


import com.project.capstone.entity.Booking;
import com.project.capstone.entity.Bus;
import com.project.capstone.entity.User;
import com.project.capstone.repository.BusRepository;
import com.project.capstone.repository.UserRepository;
import com.project.capstone.service.BookingService;
import com.project.capstone.service.BusService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;
    private BusRepository busRepository;
    private BusService busService;
    private UserRepository userRepository;

    @GetMapping("/bus/{id}")
    public String booking(@PathVariable("id") Long id, Model model) {
        Bus bus = busService.getById(id);
        if (bus == null) {
            model.addAttribute("message", "Bus not found!");
            return "error";
        }
        Booking booking = new Booking();
        booking.setBusName(bus.getBusName());
        booking.setDate(bus.getDate());
        booking.setTime(bus.getTime());
        booking.setBus(bus);
        model.addAttribute("booking", booking);
        return "Booking";
    }

    @PostMapping("/book")
    public String booked(@ModelAttribute("booking") Booking booking, Model model) {
        booking.setUser(userRepository.findById(returnUserId()).get());
        Bus bus=busRepository.findById(booking.getBus().getId()).get();

        if (bus == null || !busRepository.existsById(bus.getId())) {
            model.addAttribute("message", "Invalid booking data. Please try again.");
            return "error";
        }
        booking.setTripStatus("Active");
        double price = bus.getPrice() * booking.getNoOfPersons();
        booking.setTotalCalculated(price);
        bookingService.save(booking);
        model.addAttribute("booking", booking);
        return "pay";
    }


    @GetMapping("/allBookings")
    public String allBookings(Model model){
        List<Booking> bookings=bookingService.getAllById(returnUserId());
        model.addAttribute("bookings",bookings);

        return "allBookings";
    }

    @RequestMapping("/cancelBooking/{id}")
    public String cancelBooking(@PathVariable("id") Long id, Model model) {
        System.out.println(id);
        Booking booking = bookingService.getById(id);
        System.out.println(booking);
        booking.setTripStatus("Canceled");
        bookingService.save(booking);
        List<Booking> bookings=bookingService.getAllById(returnUserId());
        model.addAttribute("bookings",bookings);

        // Redirect to the allBookings page
        return "allBookings";
    }



    private Long returnUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername()).get();
        return users.getId();
    }
}

