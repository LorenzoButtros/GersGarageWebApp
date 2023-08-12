package com.garage.garage.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private User user;
    private String license;
    private String make;
    private String model;
    private List<Service> services = new ArrayList<>();
    private List<Accessory> accessories = new ArrayList<>();
    private List<Part> parts = new ArrayList<>();

    public Invoice () {

    }

    public BigDecimal calcTotal() {
        BigDecimal sum = BigDecimal.ZERO;

        for (Service service : services) {
            sum = sum.add(service.getPrice());
        }

        for (Accessory accessory : accessories) {
            sum = sum.add(accessory.getPrice());
        }

        for (Part part : parts) {
            sum = sum.add(part.getPrice());
        }

        return sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

}
