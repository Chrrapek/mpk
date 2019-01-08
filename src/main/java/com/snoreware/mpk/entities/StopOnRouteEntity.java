package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stopsOnRoute")
public class StopOnRouteEntity {

    @Id
    @Column(name = "stopNumber")
    private int stopNumber;

    @ManyToOne
    private StopEntity stop;

    @ManyToOne
    private RouteEntity route;
}
