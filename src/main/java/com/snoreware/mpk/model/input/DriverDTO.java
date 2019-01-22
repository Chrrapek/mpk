package com.snoreware.mpk.model.input;

import com.snoreware.mpk.entities.DriverEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private UUID driverId;
    private String name;
    private String surname;
    private String sex;
    private Float salary;
    private List<UUID> tramCourses;
    private List<UUID> busCourses;

    public static DriverDTO dtoFromEntity(DriverEntity driver) {
        return new DriverDTO(
                driver.getDriverId(),
                driver.getName(),
                driver.getSurname(),
                driver.getSex(),
                driver.getSalary(),
                driver.getUUIDOfTramCourses(),
                driver.getUUIDOfBusCourses());
    }
}
