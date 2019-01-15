package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutCourseDTO {
    private UUID courseId;
    private Boolean lowFloorNeeded;
    private Boolean articulated;
    private int numberOfWagons;
    private Long vehicleNumber;
    private UUID driverId;
    private Long routeNumber;

    public OutCourseDTO(UUID courseId, boolean lowFloorNeeded, boolean articulated, Long vehicleNumber, UUID driverId, Long routeNumber) {
        this.courseId = courseId;
        this.lowFloorNeeded = lowFloorNeeded;
        this.articulated = articulated;
        this.vehicleNumber = vehicleNumber;
        this.driverId = driverId;
        this.routeNumber = routeNumber;
    }

    public OutCourseDTO(UUID courseId, boolean lowFloorNeeded, int numberOfWagons, Long vehicleNumber, UUID driverId, Long routeNumber) {
        this.courseId = courseId;
        this.lowFloorNeeded = lowFloorNeeded;
        this.numberOfWagons = numberOfWagons;
        this.vehicleNumber = vehicleNumber;
        this.driverId = driverId;
        this.routeNumber = routeNumber;
    }
}
