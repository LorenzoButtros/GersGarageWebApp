package com.garage.garage.controller;

import com.garage.garage.model.Make;
import com.garage.garage.model.Vehicle;
import com.garage.garage.service.MakeService;
import com.garage.garage.service.VehicleService;
import com.garage.garage.repository.VehicleRepository;
import com.garage.garage.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller // Use @Controller instead of @RestController
@RequestMapping("admin/vehicles")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private MakeService makeService;
    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/")
    public String showReadForm(Model model) {

        Map<Integer, String> usersMap = userServiceImpl.getUserNamesMap();
        model.addAttribute("usersMap", usersMap);

        List<Make> makes = makeService.getAllMakes();
        model.addAttribute("makes", makes);

        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicle-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        List<Make> makes = makeService.getAllMakes();
        model.addAttribute("makes", makes);
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle-create";
    }

    @PostMapping
    public String createVehicle(@ModelAttribute Vehicle vehicle) {

        vehicleRepository.save(vehicle);
        return "redirect:/admin/vehicles/";
    }



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        List<Make> makes = makeService.getAllMakes();
        model.addAttribute("makes", makes);
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle-update";
    }

    @PostMapping("/update")
    public String updateVehicle(@ModelAttribute Vehicle vehicle) {

        vehicleService.updateVehicle(vehicle);
        return "redirect:/admin/vehicles/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {

        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle-delete";
    }

    @PostMapping("/delete")
    public String deleteVehicle(@ModelAttribute Vehicle vehicle) {

        vehicleService.deleteVehicle(vehicle.getId());
        return "redirect:/admin/vehicles/";
    }

}