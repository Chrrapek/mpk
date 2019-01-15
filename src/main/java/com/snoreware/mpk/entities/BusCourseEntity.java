package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
