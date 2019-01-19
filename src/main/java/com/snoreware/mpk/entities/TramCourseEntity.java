package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tram_courses", indexes = {@Index(name = "T_COURSES",
        columnList = "course_id,tram_vehicle_number,driver_driver_id,route_route_number")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"driver_driver_id", "tram_vehicle_number"}))
public class TramCourseEntity extends CourseEntity {
    public TramCourseEntity(boolean lowFloorNeeded, Long routeNumber, boolean needsManyWagons) {
        super(lowFloorNeeded);
        this.needsManyWagons = needsManyWagons;
        this.route = new RouteEntity(routeNumber);
    }

    @Column(name = "needs_many_wagons")
    private boolean needsManyWagons;

    @ManyToOne(optional = false)
    @NotNull(message = "Prosze podac istniejacego kierowce")
    private DriverEntity driver;

    @ManyToOne(optional = false)
    @NotNull(message = "Prosze podac istniejacy tramwaj")
    private TramEntity tram;

    @ManyToOne(optional = false)
    @NotNull(message = "Prosze podac istniejaca trase")
    private RouteEntity route;
}
