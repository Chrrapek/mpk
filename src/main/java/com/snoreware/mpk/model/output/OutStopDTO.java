package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutStopDTO {
    private UUID stopId;
    private String stopName;
}
