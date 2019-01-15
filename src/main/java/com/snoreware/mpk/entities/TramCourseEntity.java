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
    @NotNull
    @UniqueElements
    private DriverEntity driver;

    @ManyToOne
    @NotNull
    @UniqueElements
    private TramEntity tram;

    @ManyToOne
    @NotNull
    private RouteEntity route;
}
