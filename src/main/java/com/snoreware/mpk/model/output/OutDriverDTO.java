package com.snoreware.mpk.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutDriverDTO {
    private UUID driverId;
    private String name;
    private String surname;
}
