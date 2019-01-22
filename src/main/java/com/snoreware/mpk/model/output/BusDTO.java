package com.snoreware.mpk.model.output;

import com.snoreware.mpk.entities.BusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {

    public BusDTO(Long vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    private Long vehicleNumber;
    private Boolean articulated;
    private Boolean lowFloor;
    private List<UUID> courses;
    private Boolean breakdown;

    public static BusDTO dtoFromEntity(BusEntity bus) {
        return new BusDTO(
                bus.getVehicleNumber(),
                bus.getArticulated(),
                bus.getLowFloor(),
                bus.getUUIDList(),
                bus.getVehicleBreakdown());
    }
}
