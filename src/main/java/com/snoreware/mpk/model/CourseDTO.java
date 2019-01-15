package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private UUID courseId;
    private Long routeNumber;
    private UUID driverId;
    private Boolean manyWagonsNeeded;
    private Boolean articulatedNeeded;
    private Boolean lowFloorNeeded;
}
