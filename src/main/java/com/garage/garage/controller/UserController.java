package com.garage.garage.controller;

import com.garage.garage.model.User;
import com.garage.garage.service.UserService;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Controller // Use @Controller instead of @RestController
@RequestMapping("admin/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String showReadForm(Model model) {

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("user", new User());
        return "user-create";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user) {

        userRepository.save(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElse(new User()); // Provide a default Users object if the optional is empty
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {

        userRepository.save(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Integer id, Model model) {

        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElse(new User()); // Provide a default Users object if the optional is empty
        model.addAttribute("user", user);
        return "user-delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute User user) {

        userRepository.deleteById(user.getId());
        return "redirect:/admin/users/";
    }

}