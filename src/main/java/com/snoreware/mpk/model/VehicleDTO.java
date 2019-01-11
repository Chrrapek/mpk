package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private Long vehicleNumber;
    private Boolean vehicleBreakdown;
    private Integer numberOfWagons; //only for trams
    private Boolean articulated; //only for buses
    private Boolean lowFloor;
}
