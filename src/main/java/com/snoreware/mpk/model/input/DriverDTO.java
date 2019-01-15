package com.snoreware.mpk.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
