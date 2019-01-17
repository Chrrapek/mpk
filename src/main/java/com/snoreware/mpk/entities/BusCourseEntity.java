package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bus_courses", indexes = {@Index(name = "B_COURSES",
        columnList = "course_id,bus_vehicle_number,driver_driver_id,route_route_number")})
public class BusCourseEntity extends CourseEntity {
    public BusCourseEntity(Boolean lowFloorNeeded, Long routeNumber, Boolean needsArticulated) {
        super(lowFloorNeeded);
        this.route = new RouteEntity(routeNumber);
        this.needsArticulated = needsArticulated;
    }

    @Column(name = "needs_articulated", nullable = false)
    private boolean needsArticulated;

    @ManyToOne
    @NotNull
    @UniqueElements
    private DriverEntity driver;

    @ManyToOne
    @NotNull
    @UniqueElements
    private BusEntity bus;

    @ManyToOne
    @NotNull
    private RouteEntity route;
}
