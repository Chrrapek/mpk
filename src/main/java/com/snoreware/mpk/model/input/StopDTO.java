package com.snoreware.mpk.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopDTO {
    private UUID stopId;
    private String stopName;
    private Boolean stopBreakdown;
}
