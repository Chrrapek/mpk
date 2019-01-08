package com.snoreware.mpk.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stops")
public class StopEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String stopId;

    @Column(name = "stopBreakdown", nullable = false)
    private boolean stopBreakdown;

    @OneToMany(mappedBy = "stop")
    private List<StopOnRouteEntity> stopsOnRoutes;
}
