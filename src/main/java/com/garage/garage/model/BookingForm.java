package com.garage.garage.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate selectedDay;
    private LocalTime selectedTimeSlot;

    private String selectedDateTime;

    public LocalDate getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(LocalDate selectedDay) {
        this.selectedDay = selectedDay;
    }

    public LocalTime getSelectedTimeSlot() {
        return selectedTimeSlot;
    }

    public void setSelectedTimeSlot(LocalTime selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public String getSelectedDateTime() {
        return selectedDateTime;
    }

    public void setSelectedDateTime(String selectedDateTime) {
        this.selectedDateTime = selectedDateTime;
    }
}
