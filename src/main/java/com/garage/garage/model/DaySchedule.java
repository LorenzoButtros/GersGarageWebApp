package com.garage.garage.model;

import com.garage.garage.controller.BookingController;
import com.garage.garage.repository.BookingRepository;
import com.garage.garage.repository.ServiceRepository;
import com.garage.garage.repository.VehicleRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DaySchedule {

    private LocalDate date;
    private List<TimeSlot> timeSlots;
    private BookingRepository bookingRepository;
    private VehicleRepository vehicleRepository;
    private ServiceRepository serviceRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    // Constructor for schedule view, disregarding timeSlot availability (Admin tool)
    public DaySchedule(LocalDate date, BookingRepository bookingRepository, VehicleRepository vehicleRepository, ServiceRepository serviceRepository) {
        this.date = date;
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.timeSlots = createTimeSlotsForViewing(date);
    }

    //Constructor for timeSlot availability check (for User booking)
    public DaySchedule(LocalDate date, Vehicle vehicle, Service service, BookingRepository bookingRepository, VehicleRepository vehicleRepository, ServiceRepository serviceRepository) {
        this.date = date;
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.timeSlots = createTimeSlotsForBooking(date, vehicle, service);
    }

    // Create timeSlots disregarding availability
    private List<TimeSlot> createTimeSlotsForViewing(LocalDate date) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        List<Booking> bookingsToday = bookingRepository.findAllByDatein(date);
        logger.info("Booking found: {}", bookingsToday.size());
        // Define the LocalTimes for the time slots
        LocalTime[] timeValues = getTimeValues();

        for (LocalTime time : timeValues) {
            List<Booking> bookingsAtThisTime = new ArrayList<>();
            for (Booking booking : bookingsToday) {
                if(booking.getTimein() == time) {
                    bookingsAtThisTime.add(booking);
                    logger.info("Added Booking: {}", booking.getTimein());
                }
            }
            TimeSlot timeSlot = new TimeSlot(date, time, bookingsAtThisTime);
            timeSlots.add(timeSlot);
        }


        // Sets the space occupied by each booking in this date
        analyzeTimeSlotsSpace(timeSlots);



        return timeSlots;
    }

    private List<TimeSlot> createTimeSlotsForBooking(LocalDate date, Vehicle vehicle, Service service) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        List<Booking> bookingsToday = bookingRepository.findAllByDatein(date);

        // Define the LocalTimes for the time slots
        LocalTime[] timeValues = getTimeValues();

        for (LocalTime time : timeValues) {
            List<Booking> bookingsAtThisTime = new ArrayList<>();
            for (Booking booking : bookingsToday) {
                if(booking.getTimein() == time) {
                    bookingsAtThisTime.add(booking);
                }
            }
            TimeSlot timeSlot = new TimeSlot(date, time, bookingsAtThisTime);
            timeSlots.add(timeSlot);
        }

        // Sets the space occupied by each booking in this date
        analyzeTimeSlotsSpace(timeSlots);

        // Checks which timeSlots can fit the new booking
        analyzeNewBooking(date, timeSlots, vehicle, service);

        return timeSlots;
    }

    private LocalTime[] getTimeValues() {
        LocalTime[] timeValues = {
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 0),
                LocalTime.of(16, 30),
                LocalTime.of(17, 0)
        };
        return timeValues;
    }

    private void analyzeTimeSlotsSpace(List<TimeSlot> timeSlots) {

        for (int i = 0; i < timeSlots.size(); i++) {

            // Get space occupied by the previous timeSlot
            if (i > 0) {
                timeSlots.get(i).increaseUsedSpace(timeSlots.get(i-1).getUsedSpaceCont());
            }

            // Iterate through bookings in this day in this timeSlot
            for (Booking booking : timeSlots.get(i).getBookings()) {

                Vehicle bookingVehicle = vehicleRepository.findById(booking.getVehicle()).orElse(null);
                Service bookingService = serviceRepository.findById(booking.getService()).orElse(null);
                Integer size = TypeToSize(bookingVehicle.getType());

                timeSlots.get(i).increaseUsedSpace(size);
                if (bookingService.getTimeslots() > 1) {
                    timeSlots.get(i).increaseUsedSpaceCont(size);
                }
            }

        }
    }


    private void analyzeNewBooking(LocalDate date, List<TimeSlot> timeSlots, Vehicle newVehicle, Service newService) {

        for (int i = 0; i < timeSlots.size(); i++) {

            Integer currentUsedSpace = timeSlots.get(i).getUsedSpace();
            Integer size = TypeToSize(newVehicle.getType());

            // Check if the day is Sunday
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                timeSlots.get(i).setAvailable(false); // Make unavailable on Sundays
            }

            // Check if new vehicle will fit
            else if ((currentUsedSpace + size) > 4) {
                timeSlots.get(i).setAvailable(false); // Make unavailable if used space would go beyond limit
            }
        }

    }

    private Integer TypeToSize(String type) {
        Integer size;
        switch (type){
            case "Motorbike":
                size = 1;
                break;
            case "Car":
            case "Van":
                size = 2;
                break;
            case "Small Bus":
                size = 3;
                break;
            default:
                size = 2;
                logger.info("Switch error in DaySchedule");
        }
        return size;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
