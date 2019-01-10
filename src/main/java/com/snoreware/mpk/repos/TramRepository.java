package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.TramEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramRepository extends CrudRepository<TramEntity, Long> {

}
