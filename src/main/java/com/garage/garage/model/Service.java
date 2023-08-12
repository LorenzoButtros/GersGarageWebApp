package com.garage.garage.model;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String service;
    private Integer timeslots;
    private BigDecimal price = new BigDecimal("111111.11");

    public int getTimeSlots() {
        return timeslots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(Integer timeslots) {
        this.timeslots = timeslots;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

