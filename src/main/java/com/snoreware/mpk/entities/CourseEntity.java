package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CourseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int courseId;

    @Column(name = "lowFloorNeeded", nullable = false)
    private boolean lowFloorNeeded;

    @Column(name = "courseNumber", nullable = false)
    private int courseNumber;
}
