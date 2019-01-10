package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CourseRepository extends CrudRepository<CourseEntity, UUID> {
}
