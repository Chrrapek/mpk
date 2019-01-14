package com.snoreware.mpk.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stops")
public class StopEntity {
    @Id
    @Column(name = "stop_id", nullable = false, unique = true)
    private UUID stopId;

    @Column(name = "stop_name", nullable = false, unique = true)
    private String stopName;

    @Column(name = "stop_breakdown", nullable = false)
    private boolean stopBreakdown;

    @OneToMany(mappedBy = "stop")
    private List<StopOnRouteEntity> stopsOnRoutes = new ArrayList<>();

    public StopEntity(String stopName) {
        this.stopId = UUID.randomUUID();
        this.stopName = stopName;
        this.stopBreakdown = false;
    }

    public StopEntity(UUID stopId) {
        this.stopId = stopId;
    }
}
