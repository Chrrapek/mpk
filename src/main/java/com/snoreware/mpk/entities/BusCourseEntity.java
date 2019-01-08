package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "busCourses")
public class BusCourseEntity extends CourseEntity {
    @Column(name = "needsArticulated", nullable = false)
    private boolean needsArticulated;

    @ManyToOne
    private DriverEntity driver;

    @ManyToOne
    private BusEntity bus;

    @ManyToOne
    private RouteEntity route;
}
