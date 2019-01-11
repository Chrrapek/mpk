package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {
    @Id
    @Column(name = "routeId", nullable = false)
    private int routeId;

    @OneToMany(mappedBy = "route")
    private List<TramCourseEntity> tramCourses;

    @OneToMany(mappedBy = "route")
    private List<BusCourseEntity> busCourses;

    @OneToMany(mappedBy = "route")
    private List<StopOnRouteEntity> stopsOnRoutes;
}
