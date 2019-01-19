package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.TramEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TramRepository extends CrudRepository<TramEntity, Long> {
    TramEntity findByVehicleNumber(Long vehicleNumber);

    List<TramEntity> findByLowFloorOrderByVehicleNumberDesc(boolean lowFloor);

    List<TramEntity> findByNumberOfWagonsGreaterThanEqual(int numberOfWagons);

    List<TramEntity> findByNumberOfWagonsGreaterThanEqualAndLowFloor(int numberOfWagons, boolean lowFloor);
    List<TramEntity> findAllByOrderByVehicleNumberDesc();
}
