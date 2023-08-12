package com.garage.garage.service;

import com.garage.garage.model.Make;
import com.garage.garage.model.User;
import com.garage.garage.repository.MakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MakeService {
    @Autowired
    private MakeRepository makeRepository;

    public List<Make> getAllMakes() {
        List<Make> makes = makeRepository.findAll();
        makes.sort(Comparator.comparing(Make::getMake));
        return makes;
    }

    public Make getMakeById(Integer id) {

        return makeRepository.findById(id).orElse(null);
    }

    public Make addMake(Make make) {

        return makeRepository.save(make);
    }

    public Make updateMake(Make updatedMake) {

        return makeRepository.save(updatedMake);
    }

    public void deleteMake(Integer id) {

        makeRepository.deleteById(id);
    }

    public Map<Integer, String> getMakesMap() {

        List<Make> makes = getAllMakes();
        Map<Integer, String> makesMap = new HashMap<>();
        for (Make make : makes) {
            makesMap.put(make.getId(), make.getMake());
        }
        return makesMap;
    }
}
