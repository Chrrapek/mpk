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
@Table(name = "tramCourses")
public class TramCourseEntity extends CourseEntity {
    @Column(name = "needsManyWagons")
    private boolean needsManyWagons;

    @ManyToOne
    private DriverEntity driver;

    @ManyToOne
    private TramEntity tram;

    @ManyToOne
    private RouteEntity route;
}
