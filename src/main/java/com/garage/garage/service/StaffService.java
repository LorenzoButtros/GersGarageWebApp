package com.garage.garage.service;

import com.garage.garage.model.Staff;
import com.garage.garage.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {

        return staffRepository.findAll();
    }

    public Staff getStaffById(Integer id) {

        return staffRepository.findById(id).orElse(null);
    }

    public String getStaffNameById(Integer id) {
        Staff staff = staffRepository.findById(id).orElse(null);
        String name = staff.getName();
        return name;
    }

    public Staff createStaff(Staff staff) {

        return staffRepository.save(staff);
    }

    public Staff updateStaff(Staff updatedStaff) {

        return staffRepository.save(updatedStaff);
    }

    public void deleteStaff(Integer id) {

        staffRepository.deleteById(id);
    }

    // Get all staff of the date
    public Map<Integer, String> getStaffNamesMap() {

        List<Staff> staffs = getAllStaff();
        Map<Integer, String> staffNamesMap = new HashMap<>();
        for (Staff staff : staffs) {

            staffNamesMap.put(staff.getId(), staff.getName());
        }
        return staffNamesMap;
    }
}
