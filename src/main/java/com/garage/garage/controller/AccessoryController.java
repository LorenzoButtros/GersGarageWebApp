package com.garage.garage.controller;

import com.garage.garage.model.Accessory;
import com.garage.garage.service.AccessoryService;
import com.garage.garage.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/accessories")
public class AccessoryController {
    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("/")
    public String showReadForm(Model model) {

        List<Accessory> accessories = accessoryService.getAllAccessories();
        model.addAttribute("accessories", accessories);
        return "accessory-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("accessory", new Accessory());
        return "accessory-create";
    }

    @PostMapping
    public String createAccessory(@ModelAttribute Accessory accessory) {

        accessoryRepository.save(accessory);
        return "redirect:/admin/accessories/";
    }



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        Accessory accessory = accessoryService.getAccessoryById(id);
        model.addAttribute("accessory", accessory);
        return "accessory-update";
    }

    @PostMapping("/update")
    public String updateAccessory(@ModelAttribute Accessory accessory) {

        accessoryService.updateAccessory(accessory);
        return "redirect:/admin/accessories/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {

        Accessory accessory = accessoryService.getAccessoryById(id);
        model.addAttribute("accessory", accessory);
        return "accessory-delete";
    }

    @PostMapping("/delete")
    public String deleteAccessory(@ModelAttribute Accessory accessory) {

        accessoryService.deleteAccessory(accessory.getId());
        return "redirect:/admin/accessories/";
    }

}