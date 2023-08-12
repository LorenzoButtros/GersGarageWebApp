package com.garage.garage.repository;

import com.garage.garage.model.DayRoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface DayRosterRepository extends JpaRepository<DayRoster, DayOfWeek> {
    DayRoster findByDayOfWeek(DayOfWeek dayOfWeek);
}