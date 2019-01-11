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
    public CourseEntity(boolean lowFloorNeeded, int courseNumber) {
        this.courseId = UUID.randomUUID();
        this.lowFloorNeeded = lowFloorNeeded;
        this.courseNumber = courseNumber;
    }

    @Id
    @Column(name = "id", nullable = false)
    private UUID courseId;

    @Column(name = "lowFloorNeeded", nullable = false)
    private boolean lowFloorNeeded;

    @Column(name = "courseNumber", nullable = false)
    private int courseNumber;
}
