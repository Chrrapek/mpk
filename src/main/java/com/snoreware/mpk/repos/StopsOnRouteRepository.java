package com.snoreware.mpk.repos;

import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.StopOnRouteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopsOnRouteRepository extends CrudRepository<StopOnRouteEntity, Long> {
    void deleteByRoute(RouteEntity route);

    List<StopOnRouteEntity> findAllByRoute(RouteEntity route);
}
