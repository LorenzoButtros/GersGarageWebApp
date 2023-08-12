package com.garage.garage.service;

import com.garage.garage.model.*;
import com.garage.garage.repository.BookingRepository;
import com.garage.garage.repository.ServiceRepository;
import com.garage.garage.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    // For admin view
    public DaySchedule getDay(LocalDate date) {
        DaySchedule day = new DaySchedule(date, bookingRepository, vehicleRepository, serviceRepository);
        return day;
    }

    // For admin view
    public List<DaySchedule> get30Days() {
        List<DaySchedule> days = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            LocalDate date = currentDate.plus(i, ChronoUnit.DAYS);
            DaySchedule day = new DaySchedule(date, bookingRepository, vehicleRepository, serviceRepository);
            days.add(day);
        }

        return days;
    }

    // For user booking
    public List<DaySchedule> get30Days(Vehicle vehicle, Service service) {
        List<DaySchedule> days = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            LocalDate date = currentDate.plus(i, ChronoUnit.DAYS);
            //List<TimeSlot> timeSlots = createTimeSlots(date);
            DaySchedule day = new DaySchedule(date, vehicle, service, bookingRepository, vehicleRepository, serviceRepository);
            days.add(day);
        }

        return days;
    }



    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingRepository.findAllByDatein(date);
    }

    public Booking getBookingById(long id) {

        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(Booking booking) {

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking updatedBooking) {

        return bookingRepository.save(updatedBooking);
    }

    public void deleteBooking(long id) {

        bookingRepository.deleteById(id);
    }
}
