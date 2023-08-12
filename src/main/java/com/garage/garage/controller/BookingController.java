package com.garage.garage.controller;

import com.garage.garage.model.*;
import com.garage.garage.repository.BookingRepository;
import com.garage.garage.service.BookingService;
import com.garage.garage.service.ServiceService;
import com.garage.garage.service.VehicleService;
import com.garage.garage.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/vehicle")
    public String showBookVehiclePage(HttpSession session, Model model) {

        List<Vehicle> userVehicles = vehicleService.getVehiclesByUser(userService.getUserIdFromJwtToken(request));

        model.addAttribute("userVehicles", userVehicles);

        return "book-vehicle";
    }

    @PostMapping("/vehicle")
    public String processBookVehicleForm(@RequestParam("selectedVehicleId") Long selectedVehicleId, HttpSession session) {

        // Fetch the Vehicles model from the database using the selectedVehicleId
        Vehicle selectedVehicle = vehicleService.getVehicleById(selectedVehicleId);

        // Save the selectedVehicle into the session
        session.setAttribute("selectedVehicle", selectedVehicle);

        Vehicle sessionVehicle = (Vehicle) session.getAttribute("selectedVehicle");

        return "redirect:/booking/service";
    }

    @GetMapping("/service")
    public String showBookServicePage(HttpSession session, Model model) {

        Vehicle selectedVehicle = (Vehicle) session.getAttribute("selectedVehicle");

        // Handle the error of the vehicle sometimes not being saved into the session
        if(selectedVehicle == null) {
            return "book-error";
        }

        List<Service> services = serviceService.getAllServices();
        model.addAttribute("services", services);

        return "book-service";
    }

    @PostMapping("/service")
    public String processBookServiceForm(@RequestParam("selectedServiceId") Long selectedServiceId, HttpSession session) {

        Service selectedService = serviceService.getServiceById(selectedServiceId);

        // Save the selectedService into the session
        session.setAttribute("selectedService", selectedService);

        return "redirect:/booking/time";
    }

    @GetMapping("/time")
    public String showBookTimePage(HttpSession session, Model model) {

        Vehicle selectedVehicle = (Vehicle) session.getAttribute("selectedVehicle");
        Service selectedService = (Service) session.getAttribute("selectedService");

        // Handle the error of the vehicle/service sometimes not being saved into the session
        if(selectedVehicle == null || selectedService == null) {
            return "book-error";
        }

        List<DaySchedule> days = bookingService.get30Days(selectedVehicle, selectedService);
        BookingForm bookingForm = new BookingForm();

        // Add the days and time slots to the model
        model.addAttribute("days", days);
        model.addAttribute("bookingForm", bookingForm);

        return "book-time";
    }

    @PostMapping("/time")
    public String processBookTimeForm(@ModelAttribute("bookingForm") BookingForm bookingForm, HttpSession session) {

        Vehicle selectedVehicle = (Vehicle) session.getAttribute("selectedVehicle");
        Service selectedService = (Service) session.getAttribute("selectedService");
        // Handle the error of the vehicle/service sometimes not being saved into the session
        if(selectedVehicle == null || selectedService == null) {
            return "book-error";
        }

        // Use the bookingForm object to access selectedDateTime (contains both date and time)
        String selectedDateTime = bookingForm.getSelectedDateTime();
        String[] dateTimeParts = selectedDateTime.split(",");

        // Parse the date and time from the string using a custom formatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate selectedDay = LocalDate.parse(dateTimeParts[0], dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTimeSlot = LocalTime.parse(dateTimeParts[1], timeFormatter);

        // Save the parsed date and time into the session
        session.setAttribute("selectedDay", selectedDay);
        session.setAttribute("selectedTimeSlot", selectedTimeSlot);

        return "redirect:/booking/confirm";
    }

    @GetMapping("/confirm")
    public String showBookConfirmPage(HttpSession session, Model model) {

        Vehicle selectedVehicle = (Vehicle) session.getAttribute("selectedVehicle");
        Service selectedService = (Service) session.getAttribute("selectedService");
        LocalDate selectedDay = (LocalDate) session.getAttribute("selectedDay");
        LocalTime selectedTimeSlot = (LocalTime) session.getAttribute("selectedTimeSlot");

        // Handle the error of the vehicle/service sometimes not being saved into the session
        if(selectedVehicle == null || selectedService == null
                || selectedDay == null || selectedTimeSlot == null) {
            return "book-error";
        }

        model.addAttribute("selectedVehicle", selectedVehicle);
        model.addAttribute("selectedService", selectedService);
        model.addAttribute("selectedDay", selectedDay);
        model.addAttribute("selectedTimeSlot", selectedTimeSlot);
        return "book-confirm";
    }

    @PostMapping("/confirm")
    public String confirmBooking(HttpSession session) {

        Vehicle selectedVehicle = (Vehicle) session.getAttribute("selectedVehicle");
        Service selectedService = (Service) session.getAttribute("selectedService");
        LocalDate selectedDay = (LocalDate) session.getAttribute("selectedDay");
        LocalTime selectedTimeSlot = (LocalTime) session.getAttribute("selectedTimeSlot");

        // Handle the error of the vehicle/service sometimes not being saved into the session
        if(selectedVehicle == null || selectedService == null
                || selectedDay == null || selectedTimeSlot == null) {
            return "book-error";
        }

        // Create a new Bookings model with the selected data
        Booking booking = new Booking();
        booking.setUser(userService.getUserIdFromJwtToken(request));
        booking.setVehicle(selectedVehicle.getId());
        booking.setService(selectedService.getId());
        booking.setDatein(selectedDay);
        booking.setTimein(selectedTimeSlot);
        booking.setStatus("Booked");
        booking.setPrice(selectedService.getPrice());

        // Save the booking into the database
        bookingService.createBooking(booking);

        // Clear the session after the booking is confirmed
        session.removeAttribute("selectedVehicle");
        session.removeAttribute("selectedService");
        session.removeAttribute("selectedDay");
        session.removeAttribute("selectedTimeSlot");

        return "book-done"; // Redirect to a booking summary or thank-you page
    }

}
