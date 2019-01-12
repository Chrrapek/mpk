package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.BusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends CrudRepository<BusEntity, Long> {
    List<BusEntity> findAllByOrderByVehicleNumberAsc();
    List<BusEntity> findByArticulated(boolean articulated);
    BusEntity findByVehicleNumber(Long vehicleNumber);
}
