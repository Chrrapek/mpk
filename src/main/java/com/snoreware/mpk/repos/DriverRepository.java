package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.DriverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, UUID> {
    DriverEntity findByDriverId(UUID driverId);
}
