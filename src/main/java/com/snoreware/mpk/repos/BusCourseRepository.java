package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.BusCourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BusCourseRepository extends CrudRepository<BusCourseEntity, UUID> {
    List<BusCourseEntity> findAllByOrderByCourseIdDesc();
}
