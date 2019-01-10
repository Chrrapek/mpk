package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class VehicleEntity {
    public VehicleEntity(Long vehicleNumber, boolean lowFloor) {
        this.vehicleNumber = vehicleNumber;
        this.lowFloor = lowFloor;
        this.vehicleBreakdown = false;
    }

    @Id
    private Long vehicleNumber;

    private boolean vehicleBreakdown;

    private boolean lowFloor;
}
