package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class VehicleEntity {
    public VehicleEntity(boolean lowFloor) {
        this.lowFloor = lowFloor;
        this.vehicleBreakdown = false;
    }

    @Id
    @GeneratedValue
    private Long vehicleNumber;

    private boolean vehicleBreakdown;

    private boolean lowFloor;
}
