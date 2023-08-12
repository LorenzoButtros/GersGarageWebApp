package com.garage.garage.controller;

import com.garage.garage.model.Make;
import com.garage.garage.repository.MakeRepository;
import com.garage.garage.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Use @Controller instead of @RestController
@RequestMapping("admin/makes")
public class MakeController {
    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private MakeService makeService;

    @GetMapping("/")
    public String showReadForm(Model model) {

        List<Make> makes = makeService.getAllMakes();
        model.addAttribute("makes", makes);
        return "make-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("make", new Make());
        return "make-create";
    }

    @PostMapping
    public String createMake(@ModelAttribute Make make) {

        makeRepository.save(make);
        return "redirect:/admin/makes/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Make make = makeService.getMakeById(id);
        model.addAttribute("make", make);
        return "make-update";
    }

    @PostMapping("/update")
    public String updateMake(@ModelAttribute Make make) {

        makeService.updateMake(make);
        return "redirect:/admin/makes/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Integer id, Model model) {

        Make make = makeService.getMakeById(id);
        model.addAttribute("make", make);
        return "make-delete";
    }

    @PostMapping("/delete")
    public String deleteMake(@ModelAttribute Make make) {

        makeService.deleteMake(make.getId());
        return "redirect:/admin/makes/";
    }

}