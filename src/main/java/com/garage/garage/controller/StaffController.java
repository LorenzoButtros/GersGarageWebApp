package com.garage.garage.controller;

import com.garage.garage.model.Staff;
import com.garage.garage.repository.StaffRepository;
import com.garage.garage.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Use @Controller instead of @RestController
@RequestMapping("admin/staff")
public class StaffController {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffService staffService;

    @GetMapping("/")
    public String showReadForm(Model model) {

        List<Staff> staffs = staffService.getAllStaff();
        model.addAttribute("staffs", staffs);
        return "staff-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("staff", new Staff());
        return "staff-create";
    }

    @PostMapping
    public String createStaff(@ModelAttribute Staff staff) {

        staffRepository.save(staff);
        return "redirect:/admin/staff/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Staff staff = staffService.getStaffById(id);
        model.addAttribute("staff", staff);
        return "staff-update";
    }

    @PostMapping("/update")
    public String updateStaff(@ModelAttribute Staff staff) {

        staffService.updateStaff(staff);
        return "redirect:/admin/staff/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Integer id, Model model) {

        Staff staff = staffService.getStaffById(id);
        model.addAttribute("staff", staff);
        return "staff-delete";
    }

    @PostMapping("/delete")
    public String deleteStaff(@ModelAttribute Staff staff) {

        staffService.deleteStaff(staff.getId());
        return "redirect:/admin/staff/";
    }

}