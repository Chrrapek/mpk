package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CourseEntity {
    public CourseEntity(boolean lowFloorNeeded) {
        this.courseId = UUID.randomUUID();
        this.lowFloorNeeded = lowFloorNeeded;
    }

    @Id
    @Column(name = "course_id", nullable = false, unique = true)
    private UUID courseId;

    @Column(name = "low_floor_needed", nullable = false)
    private boolean lowFloorNeeded;
}
