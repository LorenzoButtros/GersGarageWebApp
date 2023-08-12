package com.garage.garage.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class StaffServiceCount {
    private Staff staff;
    private Integer serviceCount;

    public StaffServiceCount(Staff staff) {
        this.staff = staff;
        this.serviceCount = 0;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public void addServiceCount(Integer serviceCount) {
        this.serviceCount += serviceCount;
    }
}
