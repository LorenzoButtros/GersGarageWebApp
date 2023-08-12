package com.garage.garage.controller;

import com.garage.garage.model.Service;
import com.garage.garage.service.ServiceService;
import com.garage.garage.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/services")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/")
    public String showReadForm(Model model) {

        List<Service> services = serviceService.getAllServices();
        model.addAttribute("services", services);
        return "service-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("service", new Service());
        return "service-create";
    }

    @PostMapping
    public String createService(@ModelAttribute Service service) {

        serviceRepository.save(service);
        return "redirect:/admin/services/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        Service service = serviceService.getServiceById(id);
        model.addAttribute("service", service);
        return "service-update";
    }

    @PostMapping("/update")
    public String updateService(@ModelAttribute Service service) {

        serviceService.updateService(service);
        return "redirect:/admin/services/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {

        Service service = serviceService.getServiceById(id);
        model.addAttribute("service", service);
        return "service-delete";
    }

    @PostMapping("/delete")
    public String deleteService(@ModelAttribute Service service) {

        serviceService.deleteService(service.getId());
        return "redirect:/admin/services/";
    }
}
