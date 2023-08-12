package com.garage.garage.controller;

import com.garage.garage.model.*;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private MakeService makeService;
    @Autowired
    private PartService partService;
    @Autowired
    private AccessoryService accessoryService;


    @GetMapping("/")
    public String showHomePage(Model model) {

        List<Make> makes = getMakes(); // Get all makes except for "Other"
        model.addAttribute("makes", makes);
        return "home";
    }

    @GetMapping("/service")
    public String showServicePage(Model model) {

        List<Service> services = getServicesFromServices(); // Get all non-repair services
        model.addAttribute("services", services);
        return "service";
    }

    @GetMapping("/repair")
    public String showRepairPage(Model model) {

        List<Service> repairs = getRepairsFromServices(); // Get all repair services
        model.addAttribute("repairs", repairs);
        return "repair";
    }

    @GetMapping("/about")
    public String showAboutPage() {

        return "about";
    }

    @GetMapping("/denied")
    public String showDeniedPage() {
        return "denied";
    }

    private List<Make> getMakes() {

        List<Make> makes = new ArrayList<>();
        for (Make make : makeService.getAllMakes()) {
            if (!make.getMake().contains("Other")) {
                makes.add(make);
            }
        }
        return makes;
    }

    private List<Service> getServicesFromServices() {

        List<Service> services = new ArrayList<>();
        for (Service service : serviceService.getAllServices()) {
            if (!service.getService().contains("Repair")
                && !service.getService().contains("-")) {
                services.add(service);
            }
        }
        return services;
    }

    private List<Service> getRepairsFromServices() {

        List<Service> repairs = new ArrayList<>();
        for (Service service : serviceService.getAllServices()) {
            if (service.getService().contains("Repair")) {
                repairs.add(service);
            }
        }
        return repairs;
    }

}
