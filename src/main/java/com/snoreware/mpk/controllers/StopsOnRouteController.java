package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.StopOnRouteEntity;
import com.snoreware.mpk.model.StopsOnRouteDTO;
import com.snoreware.mpk.repos.StopsOnRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/routeStops")
public class StopsOnRouteController {
    private StopsOnRouteRepository repository;

    public StopsOnRouteController(StopsOnRouteRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/modify")
    @Transactional
    public ResponseEntity addOrModifyRoute(@RequestBody StopsOnRouteDTO dto) {
        RouteEntity route = new RouteEntity(dto.getRouteNumber());
        repository.deleteByRoute(route);

        List<UUID> stopsFromDto = dto.getStops();

        for (int i = 0; i < stopsFromDto.size(); i++) {
            StopOnRouteEntity newStop = new StopOnRouteEntity(
                    i + 1,
                    new StopEntity(stopsFromDto.get(i)),
                    route);

            repository.save(newStop);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getOne")
    public ResponseEntity<List<StopOnRouteEntity>> getOneRoute(@RequestBody StopsOnRouteDTO dto) {
        RouteEntity route = new RouteEntity(dto.getRouteNumber());
        List<StopOnRouteEntity> stops = repository.findByRoute(new RouteEntity(dto.getRouteNumber()));

        return ResponseEntity.ok().body(stops);
    }
}
