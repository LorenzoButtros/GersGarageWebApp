package com.garage.garage.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeSlot {
    private LocalDate date;
    private LocalTime time;
    private List<Booking> bookings;
    private Integer usedSpace;
    private Integer usedSpaceCont;
    private boolean available;

    public TimeSlot(LocalDate date, LocalTime time, List<Booking> bookings) {
        this.date = date;
        this.time = time;
        this.bookings = bookings;
        this.usedSpace = 0;
        this.usedSpaceCont = 0;
        this.available = true;
    }

    // No-argument constructor
    public TimeSlot() {
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Integer getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(Integer usedSpace) {
        this.usedSpace = usedSpace;
    }

    public void increaseUsedSpace(Integer space) {
        this.usedSpace += space;
    }

    public Integer getUsedSpaceCont() {
        return usedSpaceCont;
    }

    public void setUsedSpaceCont(Integer usedSpaceCont) {
        this.usedSpaceCont = usedSpaceCont;
    }

    public void increaseUsedSpaceCont(Integer space) {
        this.usedSpaceCont += space;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
