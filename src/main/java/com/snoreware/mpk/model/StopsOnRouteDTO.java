package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopsOnRouteDTO {
    private long routeNumber;
    private List<UUID> stops;
}
