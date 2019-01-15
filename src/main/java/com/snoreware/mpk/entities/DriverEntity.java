package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drivers")
public class DriverEntity {
    public DriverEntity(String name, String surname, String sex, Float salary) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.salary = salary;
    }

    public DriverEntity(UUID driverId) {
        this.driverId = driverId;
    }

    @Id
    @GeneratedValue
    @Column(name = "driver_id", nullable = false, unique = true)
    private UUID driverId;

    @Column(name = "driver_name", nullable = false)
    private String name;

    @Column(name = "driver_surname", nullable = false)
    private String surname;

    @Column(name = "driver_sex", nullable = false)
    private String sex;

    @Column(name = "driver_salary", nullable = false)
    private Float salary;

    @Column(name = "driver_seniority")
    private Integer seniority;

    @OneToMany(mappedBy = "driver")
    private List<TramCourseEntity> tramCourses;
}
