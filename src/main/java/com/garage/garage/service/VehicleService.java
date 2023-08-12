package com.garage.garage.service;

import com.garage.garage.model.Vehicle;
import com.garage.garage.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {

        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(long id) {

        return vehicleRepository.findById(id).orElse(null);
    }

    public List<Vehicle> getVehiclesByUser(Integer userId) {
        return vehicleRepository.findByUser(userId);
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle updatedVehicle) {

        return vehicleRepository.save(updatedVehicle);
    }

    public void deleteVehicle(long id) {

        vehicleRepository.deleteById(id);
    }

    public Map<Long, String> getVehicleLicensesMap() {

        List<Vehicle> vehicles = getAllVehicles();
        Map<Long, String> vehicleLicensesMap = new HashMap<>();
        for (Vehicle vehicle : vehicles) {

            vehicleLicensesMap.put(vehicle.getId(), vehicle.getLicense());
        }
        return vehicleLicensesMap;
    }
}
