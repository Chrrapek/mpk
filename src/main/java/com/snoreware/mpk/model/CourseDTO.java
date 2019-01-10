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
    private boolean manyWagonsNeeded;
    private boolean articulatedNeeded;
    private boolean lowFloorNeeded;
    private int courseNumber;
}
