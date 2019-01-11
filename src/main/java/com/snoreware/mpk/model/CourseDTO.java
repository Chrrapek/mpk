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
    private Boolean manyWagonsNeeded;
    private Boolean articulatedNeeded;
    private Boolean lowFloorNeeded;
    private Integer courseNumber;
}
