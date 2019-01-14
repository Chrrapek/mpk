package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routes")
public class RouteEntity {
    public RouteEntity(Long routeNumber) {
        this.routeNumber = routeNumber;
    }

    @Id
    @Column(name = "route_number", nullable = false, unique = true)
    private Long routeNumber;

    @OneToMany(mappedBy = "route")
    private List<TramCourseEntity> tramCourses = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<BusCourseEntity> busCourses = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<StopOnRouteEntity> stopsOnRoutes = new ArrayList<>();
}
