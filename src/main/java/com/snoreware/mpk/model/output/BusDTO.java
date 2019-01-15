package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {
    private Long vehicleNumber;
    private Boolean articulated;
    private Boolean lowFloor;
    private List<UUID> courses;
    private Boolean breakdown;
}
