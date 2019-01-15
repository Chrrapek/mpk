package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutStopDTO {
    public OutStopDTO(UUID stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }

    private int stopNumber;
    private UUID stopId;
    private String stopName;
}
