package com.garage.garage.service;

import com.garage.garage.model.Service;
import com.garage.garage.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllServices() {

        return serviceRepository.findAll();
    }

    public Service getServiceById(long id) {

        return serviceRepository.findById(id).orElse(null);
    }

    public Service createService(Service service) {

        return serviceRepository.save(service);
    }

    public Service updateService(Service updatedService) {

        return serviceRepository.save(updatedService);
    }

    public void deleteService(long id) {

        serviceRepository.deleteById(id);
    }

    public Map<Long, String> getServiceNamesMap() {

        List<Service> services = getAllServices();
        Map<Long, String> serviceNamesMap = new HashMap<>();
        for (Service service : services) {

            serviceNamesMap.put(service.getId(), service.getService());
        }
        return serviceNamesMap;
    }
}
