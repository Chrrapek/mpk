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
    @Id
    private int vehicleNumber;

    private boolean vehicleBreakdown;

    private boolean lowFloor;
}
