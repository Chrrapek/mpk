package com.snoreware.mpk.entities;

import com.snoreware.mpk.model.input.StopOnRouteKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stops_on_route")
@IdClass(StopOnRouteKey.class)
public class StopOnRouteEntity {

    @Id
    @Column(name = "stop_number")
    private int stopNumber;

    @Id
    @ManyToOne
    private StopEntity stop;

    @Id
    @ManyToOne
    private RouteEntity route;
}
