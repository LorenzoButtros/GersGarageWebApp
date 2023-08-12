package com.garage.garage.controller;

import com.garage.garage.model.Make;
import com.garage.garage.model.User;
import com.garage.garage.model.Vehicle;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.MakeService;
import com.garage.garage.service.VehicleService;
import com.garage.garage.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private MakeService makeService;

    @GetMapping("/profile")
    public String showProfile(Model model, HttpServletRequest request) {

        Integer userId = userService.getUserIdFromJwtToken(request);
        User user = userService.loadUserById(userId);
        model.addAttribute("user", user);
        return "account-profile";
    }

    @GetMapping("/vehicles")
    public String showVehicles(Model model, HttpServletRequest request) {

        Integer userId = userService.getUserIdFromJwtToken(request);
        List<Vehicle> vehicles = vehicleService.getVehiclesByUser(userId);

        Map<Integer, String> makesMap = makeService.getMakesMap();
        model.addAttribute("makesMap", makesMap);

        model.addAttribute("vehicles", vehicles);
        return "account-vehicles";
    }

    @GetMapping("/vehicles/add")
    public String addVehicle(Model model,  HttpServletRequest request) {

        Integer userId = userService.getUserIdFromJwtToken(request);
        List<Vehicle> vehicles = vehicleService.getVehiclesByUser(userId);
        model.addAttribute("vehicles", vehicles);
        List<Make> makes = makeService.getAllMakes();

        model.addAttribute("makes", makes);
        return "account-add-vehicle";
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@ModelAttribute("vehicle") Vehicle vehicle, HttpServletRequest request) {

        // Set the user for the vehicle using the userId obtained from the JWT token
        Integer userId = userService.getUserIdFromJwtToken(request);
        vehicle.setUser(userId);

        vehicleService.createVehicle(vehicle);

        return "redirect:/account/vehicles";
    }

    @GetMapping("/cart")
    public String showCart() {

        return "account-cart";
    }

}
/*

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new Users());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Users user, Model model) {
        // Save the user in the database
        Users isUserCreated = userRepository.save(user);

        if (isUserCreated != null) {
            model.addAttribute("user", user);
            return "confirmation"; // Return the view name for the confirmation page
        } else {
            // Handle the case when user creation fails
            return "redirect:/signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "log-in"; // Assuming your login page is named "signin.html" or "login.jsp"
    }


    @PostMapping("/login")
    public String processLogin(HttpServletRequest request, @RequestParam String login, @RequestParam String password) {
        Optional<Users> user = userRepository.findByEmail(login);

        if (user != null && user.getPassword().equals(password)) {
            // Set session attribute to mark user as authenticated
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else if (user == null) {
            return "redirect:/service";
        } else if (!user.getPassword().equals(password)) {
            return "redirect:/contact";
        } else {
            return "redirect:/shop";
        }
    }
*/


/*
spring.security.user.name=admin
spring.security.user.password=admin
spring.security.user.roles=admin
*/