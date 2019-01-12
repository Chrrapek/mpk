package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.RouteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
    List<RouteEntity> findByOrderByRouteNumberAsc();
    RouteEntity findByRouteNumber(Long routeNumber);
}
