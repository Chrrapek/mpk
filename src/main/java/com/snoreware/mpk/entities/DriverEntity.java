package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drivers")
public class DriverEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String driverId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "salary", nullable = false)
    private Float salary;

    @Column(name = "seniority")
    private int seniority;

    @OneToMany(mappedBy = "driver")
    private List<TramCourseEntity> tramCourses;
}
