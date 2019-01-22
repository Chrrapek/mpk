package com.snoreware.mpk.model.output;

import com.snoreware.mpk.entities.BusCourseEntity;
import com.snoreware.mpk.entities.TramCourseEntity;
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

    public static OutCourseDTO dtoFromBusCourse(BusCourseEntity busCourse) {
        return new OutCourseDTO(
                busCourse.getCourseId(),
                busCourse.getBus().getLowFloor(),
                busCourse.getBus().getArticulated(),
                busCourse.getBus().getVehicleNumber(),
                busCourse.getDriver().getDriverId(),
                busCourse.getRoute().getRouteNumber());
    }

    public static OutCourseDTO dtoFromTramCourse(TramCourseEntity tramCourse) {
        return new OutCourseDTO(
                tramCourse.getCourseId(),
                tramCourse.getTram().getLowFloor(),
                tramCourse.getTram().getNumberOfWagons(),
                tramCourse.getTram().getVehicleNumber(),
                tramCourse.getDriver().getDriverId(),
                tramCourse.getRoute().getRouteNumber());
    }
}
