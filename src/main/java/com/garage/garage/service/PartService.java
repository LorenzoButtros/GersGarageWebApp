package com.garage.garage.service;

import com.garage.garage.model.Part;
import com.garage.garage.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    public List<Part> getAllParts() {

        return partRepository.findAll();
    }

    public List<Part> getPartsByPart(String partType) {

        partType = partType.replace("_"," ");
        List<Part> parts = new ArrayList<>();
        for (Part part : partRepository.findAll()) {
            if (part.getPart().equals(partType)) {
                parts.add(part);
            }
        }
        return parts;
    }

    public Part getPartById(long id) {

        return partRepository.findById(id).orElse(null);
    }

    public Part createPart(Part part) {

        return partRepository.save(part);
    }

    public Part updatePart(Part updatedPart) {

        return partRepository.save(updatedPart);
    }

    public void deletePart(long id) {

        partRepository.deleteById(id);
    }
}
