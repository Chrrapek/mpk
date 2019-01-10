package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.StopEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StopRepository extends CrudRepository<StopEntity, UUID> {

}
