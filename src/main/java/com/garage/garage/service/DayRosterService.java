package com.garage.garage.service;

import com.garage.garage.model.DayRoster;
import com.garage.garage.repository.DayRosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class DayRosterService {
    @Autowired
    private DayRosterRepository dayRosterRepository;

    public List<DayRoster> getAllDayRosters() {

        return dayRosterRepository.findAll();
    }

    public DayRoster getDayRosterByDayOfWeek(DayOfWeek dayOfWeek) {

        return dayRosterRepository.findByDayOfWeek(dayOfWeek);
    }

    public DayRoster updateDayRoster(DayRoster updatedDayRoster) {

        return dayRosterRepository.save(updatedDayRoster);
    }

}
