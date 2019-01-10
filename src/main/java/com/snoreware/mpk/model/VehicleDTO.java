package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private Long vehicleNumber;
    private boolean vehicleBreakdown;
    private int numberOfWagons; //only for trams
    private boolean articulated; //only for buses
    private boolean lowFloor;
}
