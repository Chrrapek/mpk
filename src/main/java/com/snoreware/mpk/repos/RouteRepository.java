package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.RouteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
    RouteEntity findByRouteNumber(Long routeNumber);
}
