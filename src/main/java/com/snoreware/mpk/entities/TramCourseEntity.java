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
@Table(name = "tram_courses")
public class TramCourseEntity extends CourseEntity {
    public TramCourseEntity(boolean lowFloorNeeded, Long routeNumber, boolean needsManyWagons) {
        super(lowFloorNeeded);
        this.needsManyWagons = needsManyWagons;
        this.route = new RouteEntity(routeNumber);
    }

    @Column(name = "needs_many_wagons")
    private boolean needsManyWagons;

    @ManyToOne
    private DriverEntity driver;

    @ManyToOne
    private TramEntity tram;

    @ManyToOne
    private RouteEntity route;
}
