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
@Table(name = "bus_courses")
public class BusCourseEntity extends CourseEntity {
    public BusCourseEntity(Boolean lowFloorNeeded, Long routeNumber, Boolean needsArticulated) {
        super(lowFloorNeeded);
        this.route = new RouteEntity(routeNumber);
        this.needsArticulated = needsArticulated;
    }

    @Column(name = "needs_articulated", nullable = false)
    private boolean needsArticulated;

    @ManyToOne
    private DriverEntity driver;

    @ManyToOne
    private BusEntity bus;

    @ManyToOne
    private RouteEntity route;
}
