package com.garage.garage.controller;

import com.garage.garage.model.*;
import com.garage.garage.repository.BookingRepository;
import com.garage.garage.service.*;
import com.garage.garage.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private MakeService makeService;
    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private PartService partService;

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("")
    public String showShopPage(Model model) {

        List<Part> parts = partService.getAllParts(); // Get all parts from DB
        model.addAttribute("parts", parts);
        List<Accessory> accessories = accessoryService.getAllAccessories(); // Get all accs from DB
        model.addAttribute("accessories", accessories);
        return "shop";
    }

    @GetMapping("/section/{section}")
    public String showSection(@PathVariable("section") String section, Model model) {

        List<Accessory> accessories = accessoryService.getAccessoriesBySection(section);

        model.addAttribute("section", section);
        model.addAttribute("accessories", accessories);
        return "shop-accs";
    }

    @GetMapping("/accessory/{id}")
    public String showAccessory(@PathVariable("id") Long id, Model model) {

        Accessory accessory = accessoryService.getAccessoryById(id);
        model.addAttribute("accessory", accessory);
        return "shop-acc";
    }

    @GetMapping("/makes/{partType}")
    public String showPart(@PathVariable("partType") String partType, Model model) {

        Map<Integer, String> makeNamesMap = getMakeNamesMap();
        List<Part> parts = partService.getPartsByPart(partType);
        model.addAttribute("partType", partType.replace("_", " "));
        model.addAttribute("makeNamesMap", makeNamesMap);
        model.addAttribute("parts", parts);
        return "shop-parts";
    }

    @GetMapping("/part/{id}")
    public String showPart(@PathVariable("id") Long id, Model model) {

        Part part = partService.getPartById(id);
        model.addAttribute("part", part);
        return "shop-part";
    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "shop-cart";
    }

    // All makes Map
    public Map<Integer, String> getMakeNamesMap() {
        List<Make> makes = makeService.getAllMakes();
        Map<Integer, String> makeNamesMap = new HashMap<>();
        for (Make make : makes) {
            makeNamesMap.put(make.getId(), make.getMake());
        }
        return makeNamesMap;
    }

}

