package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.DriverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, Long> {
    DriverEntity findByDriverId(Long driverId);
}
