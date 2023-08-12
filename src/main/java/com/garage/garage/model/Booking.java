package com.garage.garage.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer user;
    private Long vehicle;
    private Long service;
    private LocalDate datein;
    private LocalTime timein;
    private LocalDate dateout;
    private LocalTime timeout;
    private String status;
    private BigDecimal price = new BigDecimal("111111.11");
    private Integer staff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Long getVehicle() {
        return vehicle;
    }

    public void setVehicle(Long vehicle) {
        this.vehicle = vehicle;
    }

    public Long getService() {
        return service;
    }

    public void setService(Long service) {
        this.service = service;
    }

    public LocalDate getDatein() { return datein; }

    public void setDatein(LocalDate datein) { this.datein = datein; }

    public LocalTime getTimein() { return timein; }

    public void setTimein(LocalTime timein) { this.timein = timein; }

    public LocalDate getDateout() { return dateout; }

    public void setDateout(LocalDate dateout) { this.dateout = dateout; }

    public LocalTime getTimeout() { return timeout; }

    public void setTimeout(LocalTime timeout) { this.timeout = timeout; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }
}

