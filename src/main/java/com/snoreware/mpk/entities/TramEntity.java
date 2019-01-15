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
@Table(name = "trams")
public class TramEntity extends VehicleEntity {
    public TramEntity(boolean lowFloor, int numberOfWagons) {
        super(lowFloor);
        this.numberOfWagons = numberOfWagons;
    }

    public TramEntity(Long vehicleNumber) {
        super(vehicleNumber);
    }

    public List<UUID> getUUIDList() {
        List<UUID> result = new ArrayList<>();
        for (TramCourseEntity course : tramCourses)
            result.add(course.getCourseId());

        return result;
    }

    @Column(name = "number_of_wagons")
    private int numberOfWagons;

    @OneToMany(mappedBy = "tram")
    private List<TramCourseEntity> tramCourses;
}
