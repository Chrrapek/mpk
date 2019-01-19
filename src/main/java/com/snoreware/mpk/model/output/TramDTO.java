package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramDTO {
    private Long vehicleNumber;
    private int numberOfWagons;
    private Boolean lowFloor;
    private List<UUID> courses;
    private Boolean breakdown;

    public TramDTO(Long vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
