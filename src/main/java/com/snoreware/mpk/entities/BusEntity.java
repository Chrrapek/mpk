package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buses")
public class BusEntity extends VehicleEntity {
    public BusEntity(Boolean lowFloor, Boolean articulated) {
        super(lowFloor);
        this.articulated = articulated;
    }

    public BusEntity(Long vehicleNumber) {
        super(vehicleNumber);
    }

    public List<UUID> getUUIDList() {
        List<UUID> result = new ArrayList<>();
        for (BusCourseEntity course : busCourses)
            result.add(course.getCourseId());

        return result;
    }

    @Column(name = "articulated", nullable = false)
    private Boolean articulated;

    @OneToMany(mappedBy = "bus")
    private List<BusCourseEntity> busCourses;
}
