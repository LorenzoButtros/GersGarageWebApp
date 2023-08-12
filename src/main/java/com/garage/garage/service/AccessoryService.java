package com.garage.garage.service;

import com.garage.garage.model.Accessory;
import com.garage.garage.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessoryService {
    @Autowired
    private AccessoryRepository accessoryRepository;

    public List<Accessory> getAllAccessories() {

        return accessoryRepository.findAll();
    }

    public List<Accessory> getAccessoriesBySection(String section) {

        List<Accessory> accessories = new ArrayList<>();
        for (Accessory accessory : accessoryRepository.findAll()) {
            if (accessory.getSection().equals(section)) {
                accessories.add(accessory);
            }
        }
        return accessories;
    }

    public Accessory getAccessoryById(long id) {

        return accessoryRepository.findById(id).orElse(null);
    }

    public Accessory createAccessory(Accessory accessory) {

        return accessoryRepository.save(accessory);
    }

    public Accessory updateAccessory(Accessory updatedAccessory) {

        return accessoryRepository.save(updatedAccessory);
    }

    public void deleteAccessory(long id) {

        accessoryRepository.deleteById(id);
    }
}
