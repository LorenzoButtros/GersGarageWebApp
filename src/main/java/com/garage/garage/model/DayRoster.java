package com.garage.garage.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Entity
public class DayRoster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING) // Specify EnumType.STRING for the DayOfWeek enum
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    private Integer staffId1, staffId2, staffId3, staffId4;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStaffId1() {
        return staffId1;
    }

    public void setStaffId1(Integer staffId1) {
        this.staffId1 = staffId1;
    }

    public Integer getStaffId2() {
        return staffId2;
    }

    public void setStaffId2(Integer staffId2) {
        this.staffId2 = staffId2;
    }

    public Integer getStaffId3() {
        return staffId3;
    }

    public void setStaffId3(Integer staffId3) {
        this.staffId3 = staffId3;
    }

    public Integer getStaffId4() {
        return staffId4;
    }

    public void setStaffId4(Integer staffId4) {
        this.staffId4 = staffId4;
    }
}

