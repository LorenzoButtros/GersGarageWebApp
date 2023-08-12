package com.garage.garage.controller;

import com.garage.garage.service.MakeService;
import com.garage.garage.service.PartService;
import com.garage.garage.model.Part;
import com.garage.garage.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller // Use @Controller instead of @RestController
@RequestMapping("admin/parts")
public class PartController {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private PartService partService;
    @Autowired
    private MakeService makeService;

    @GetMapping("/")
    public String showReadForm(Model model) {

        Map<Integer, String> makesMap = makeService.getMakesMap();
        model.addAttribute("makesMap", makesMap);

        List<Part> parts = partService.getAllParts();
        model.addAttribute("parts", parts);
        return "part-read";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("part", new Part());
        return "part-create";
    }

    @PostMapping
    public String createPart(@ModelAttribute Part part) {

        partRepository.save(part);
        return "redirect:/admin/parts/";
    }



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        Part part = partService.getPartById(id);
        model.addAttribute("part", part);
        return "part-update";
    }

    @PostMapping("/update")
    public String updatePart(@ModelAttribute Part part) {

        partService.updatePart(part);
        return "redirect:/admin/parts/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {

        Part part = partService.getPartById(id);
        model.addAttribute("part", part);
        return "part-delete";
    }

    @PostMapping("/delete")
    public String deletePart(@ModelAttribute Part part) {

        partService.deletePart(part.getId());
        return "redirect:/admin/parts/";
    }

}