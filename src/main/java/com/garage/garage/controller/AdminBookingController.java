package com.garage.garage.controller;

import com.garage.garage.model.Booking;
import com.garage.garage.service.BookingService;
import com.garage.garage.repository.BookingRepository;
import com.garage.garage.service.ServiceService;
import com.garage.garage.service.StaffService;
import com.garage.garage.service.VehicleService;
import com.garage.garage.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/bookings")
public class AdminBookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private VehicleService vehicleService;
    @GetMapping("/")
    public String showReadForm(Model model) {

        Map<Integer, String> usersMap = userServiceImpl.getUserNamesMap();
        Map<Long, String> vehiclesMap = vehicleService.getVehicleLicensesMap();
        Map<Long, String> servicesMap = serviceService.getServiceNamesMap();

        model.addAttribute("usersMap", usersMap);
        model.addAttribute("vehiclesMap", vehiclesMap);
        model.addAttribute("servicesMap", servicesMap);

        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("booking", new Booking());
        return "booking-create";
    }

    @PostMapping
    public String createBooking(@ModelAttribute Booking booking) {

        bookingRepository.save(booking);
        return "redirect:/admin/bookings/";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "booking-update";
    }

    @PostMapping("/update")
    public String updateBooking(@ModelAttribute Booking booking) {

        bookingService.updateBooking(booking);
        return "redirect:/admin/bookings/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {

        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "booking-delete";
    }

    @PostMapping("/delete")
    public String deleteBooking(@ModelAttribute Booking booking) {

        bookingService.deleteBooking(booking.getId());
        return "redirect:/admin/bookings/";
    }

}